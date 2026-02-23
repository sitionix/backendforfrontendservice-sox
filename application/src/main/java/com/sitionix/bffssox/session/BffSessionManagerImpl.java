package com.sitionix.bffssox.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.BffLoginSessionResult;
import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSession;
import com.sitionix.bffssox.domain.BffSessionException;
import com.sitionix.bffssox.domain.BffSessionManager;
import com.sitionix.bffssox.domain.BffSessionProperties;
import com.sitionix.bffssox.domain.BffSessionStore;
import com.sitionix.bffssox.domain.ClientResponseException;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.domain.SessionResponse;
import com.sitionix.bffssox.domain.SessionUser;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BffSessionManagerImpl implements BffSessionManager {

    private final AuthUserClient authUserClient;

    private final BffSessionStore bffSessionStore;

    private final BffSessionProperties bffSessionProperties;

    private final SessionIdGenerator sessionIdGenerator;

    private final ObjectMapper objectMapper;

    private final Clock clock;

    private final ConcurrentMap<String, ReentrantLock> refreshLocks = new ConcurrentHashMap<>();

    public BffSessionManagerImpl(final AuthUserClient authUserClient,
                                 final BffSessionStore bffSessionStore,
                                 final BffSessionProperties bffSessionProperties,
                                 final SessionIdGenerator sessionIdGenerator,
                                 final ObjectMapper objectMapper,
                                 final Clock clock) {
        this.authUserClient = authUserClient;
        this.bffSessionStore = bffSessionStore;
        this.bffSessionProperties = bffSessionProperties;
        this.sessionIdGenerator = sessionIdGenerator;
        this.objectMapper = objectMapper;
        this.clock = clock;
    }

    @Override
    public BffLoginSessionResult createSession(final LoginRequest request, final String userAgent, final String remoteAddress) {
        final String authSessionSourceId = UUID.randomUUID().toString();
        final LoginRequest authLoginRequest = LoginRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .siteId(request.getSiteId())
                .sessionSourceId(authSessionSourceId)
                .userAgent(userAgent)
                .build();
        final com.sitionix.bffssox.domain.LoginResponse authLoginResponse = this.authUserClient.login(authLoginRequest);
        final Instant now = this.clock.instant();
        final TokenClaims claims = this.parseClaims(authLoginResponse.getAccessToken());
        final BffSession session = BffSession.builder()
                .userId(claims.userId())
                .email(claims.email())
                .role(claims.role())
                .siteId(claims.siteId())
                .authSessionSourceId(authSessionSourceId)
                .accessToken(authLoginResponse.getAccessToken())
                .accessTokenExpiresAt(claims.expiresAt())
                .refreshToken(authLoginResponse.getRefreshToken())
                .refreshTokenExpiresAt(now.plusSeconds(this.bffSessionProperties.getAbsoluteTimeoutSeconds()))
                .csrfToken(this.sessionIdGenerator.newCsrfToken())
                .createdAt(now)
                .lastUsedAt(now)
                .lastRotatedAt(now)
                .maxExpiresAt(now.plusSeconds(this.bffSessionProperties.getAbsoluteTimeoutSeconds()))
                .build();
        final String sessionId = this.bffSessionStore.create(session);
        return BffLoginSessionResult.builder()
                .sessionId(sessionId)
                .csrfToken(session.getCsrfToken())
                .response(this.toLoginResponse(session))
                .build();
    }

    @Override
    public Optional<BffResolvedSession> resolveSession(final String sessionId, final String userAgent, final String remoteAddress) {
        if (sessionId == null || sessionId.isBlank()) {
            return Optional.empty();
        }
        final Optional<BffSession> sessionOptional = this.bffSessionStore.get(sessionId);
        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }
        final Instant now = this.clock.instant();
        final BffSession session = sessionOptional.get();
        if (this.isInvalid(session, now)) {
            this.invalidateSession(sessionId);
            return Optional.empty();
        }
        final Optional<BffResolvedSession> refreshedSession = this.refreshSession(sessionId, false);
        if (refreshedSession.isEmpty()) {
            return Optional.empty();
        }
        BffResolvedSession resolvedSession = refreshedSession.get();
        if (this.shouldRotate(resolvedSession.getSession(), now)) {
            final String newSessionId = this.bffSessionStore.rotate(resolvedSession.getSessionId(), now);
            final Optional<BffSession> rotatedSession = this.bffSessionStore.get(newSessionId);
            if (rotatedSession.isEmpty()) {
                return Optional.empty();
            }
            resolvedSession = BffResolvedSession.builder()
                    .previousSessionId(resolvedSession.getSessionId())
                    .sessionId(newSessionId)
                    .session(rotatedSession.get())
                    .build();
        }
        if (this.shouldTouch(resolvedSession.getSession(), now)) {
            this.bffSessionStore.touch(resolvedSession.getSessionId(), now);
            final BffSession touchedSession = resolvedSession.getSession().toBuilder()
                    .lastUsedAt(now)
                    .build();
            this.bffSessionStore.update(resolvedSession.getSessionId(), touchedSession);
            resolvedSession = BffResolvedSession.builder()
                    .previousSessionId(resolvedSession.getPreviousSessionId())
                    .sessionId(resolvedSession.getSessionId())
                    .session(touchedSession)
                    .build();
        }
        return Optional.of(resolvedSession);
    }

    @Override
    public Optional<BffResolvedSession> refreshSession(final String sessionId, final boolean forceRefresh) {
        if (sessionId == null || sessionId.isBlank()) {
            return Optional.empty();
        }
        final Optional<BffSession> existingSession = this.bffSessionStore.get(sessionId);
        if (existingSession.isEmpty()) {
            return Optional.empty();
        }
        if (!forceRefresh && !this.shouldRefresh(existingSession.get(), this.clock.instant())) {
            return Optional.of(BffResolvedSession.builder()
                    .sessionId(sessionId)
                    .session(existingSession.get())
                    .build());
        }
        final ReentrantLock lock = this.refreshLocks.computeIfAbsent(sessionId, key -> new ReentrantLock());
        lock.lock();
        try {
            final Optional<BffSession> lockedSession = this.bffSessionStore.get(sessionId);
            if (lockedSession.isEmpty()) {
                return Optional.empty();
            }
            final Instant now = this.clock.instant();
            final BffSession session = lockedSession.get();
            if (this.isInvalid(session, now)) {
                this.invalidateSession(sessionId);
                return Optional.empty();
            }
            if (!forceRefresh && !this.shouldRefresh(session, now)) {
                return Optional.of(BffResolvedSession.builder()
                        .sessionId(sessionId)
                        .session(session)
                        .build());
            }
            final RefreshAccessTokenResponse refreshResponse;
            try {
                refreshResponse = this.refreshTokens(session);
            } catch (ClientResponseException ex) {
                final int status = ex.getStatusCode();
                if (status == 401 || status == 403) {
                    this.invalidateSession(sessionId);
                    return Optional.empty();
                }
                throw ex;
            }
            final TokenClaims claims = this.parseClaims(refreshResponse.getAccessToken());
            final BffSession updatedSession = session.toBuilder()
                    .accessToken(refreshResponse.getAccessToken())
                    .accessTokenExpiresAt(claims.expiresAt())
                    .refreshToken(refreshResponse.getRefreshToken())
                    .build();
            this.bffSessionStore.update(sessionId, updatedSession);
            return Optional.of(BffResolvedSession.builder()
                    .sessionId(sessionId)
                    .session(updatedSession)
                    .build());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void invalidateSession(final String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return;
        }
        this.bffSessionStore.delete(sessionId);
    }

    private RefreshAccessTokenResponse refreshTokens(final BffSession session) {
        return this.authUserClient.refreshAccessToken(RefreshAccessTokenRequest.builder()
                .refreshToken(session.getRefreshToken())
                .sessionSourceId(session.getAuthSessionSourceId())
                .build());
    }

    private SessionResponse toLoginResponse(final BffSession session) {
        return SessionResponse.builder()
                .authenticated(Boolean.TRUE)
                .user(SessionUser.builder()
                        .id(session.getUserId())
                        .email(session.getEmail())
                        .role(session.getRole())
                        .siteId(session.getSiteId())
                        .build())
                .expiresAt(session.getMaxExpiresAt())
                .idleTimeoutSeconds(this.bffSessionProperties.getIdleTimeoutSeconds())
                .build();
    }

    private boolean shouldRefresh(final BffSession session, final Instant now) {
        final Instant accessTokenExpiresAt = session.getAccessTokenExpiresAt();
        if (accessTokenExpiresAt == null) {
            return true;
        }
        return !accessTokenExpiresAt.isAfter(now.plusSeconds(this.bffSessionProperties.getRefreshSkewSeconds()));
    }

    private boolean shouldTouch(final BffSession session, final Instant now) {
        final Instant lastUsedAt = session.getLastUsedAt();
        if (lastUsedAt == null) {
            return true;
        }
        return !lastUsedAt.isAfter(now.minusSeconds(this.bffSessionProperties.getTouchThrottleSeconds()));
    }

    private boolean shouldRotate(final BffSession session, final Instant now) {
        final Instant lastRotatedAt = session.getLastRotatedAt();
        if (lastRotatedAt == null) {
            return true;
        }
        return !lastRotatedAt.isAfter(now.minusSeconds(this.bffSessionProperties.getRotationIntervalSeconds()));
    }

    private boolean isInvalid(final BffSession session, final Instant now) {
        if (session.getLastUsedAt() == null || session.getMaxExpiresAt() == null) {
            return true;
        }
        final Instant idleExpiry = session.getLastUsedAt().plusSeconds(this.bffSessionProperties.getIdleTimeoutSeconds());
        if (!idleExpiry.isAfter(now)) {
            return true;
        }
        return !session.getMaxExpiresAt().isAfter(now);
    }

    private TokenClaims parseClaims(final String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new BffSessionException("Access token is empty");
        }
        try {
            final String[] parts = accessToken.split("\\.");
            if (parts.length < 2) {
                throw new BffSessionException("Access token has invalid JWT format");
            }
            final byte[] payload = Base64.getUrlDecoder().decode(parts[1]);
            final JsonNode claims = this.objectMapper.readTree(new String(payload, StandardCharsets.UTF_8));
            final String userId = claims.path("sub").asText(null);
            final String email = claims.path("email").asText(null);
            final String role = claims.path("role").asText(null);
            final String siteIdRaw = claims.path("siteId").asText(null);
            final long exp = claims.path("exp").asLong(0L);
            if (userId == null || email == null || role == null || exp <= 0L) {
                throw new BffSessionException("Access token misses required claims");
            }
            final UUID siteId = siteIdRaw == null || siteIdRaw.isBlank() ? null : UUID.fromString(siteIdRaw);
            return new TokenClaims(userId, email, role, siteId, Instant.ofEpochSecond(exp));
        } catch (BffSessionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BffSessionException("Unable to parse access token claims");
        }
    }

    private record TokenClaims(String userId, String email, String role, UUID siteId, Instant expiresAt) {
    }
}
