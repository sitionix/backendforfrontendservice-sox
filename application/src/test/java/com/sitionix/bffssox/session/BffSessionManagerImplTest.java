package com.sitionix.bffssox.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.BffLoginSessionResult;
import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSession;
import com.sitionix.bffssox.domain.BffSessionProperties;
import com.sitionix.bffssox.domain.BffSessionStore;
import com.sitionix.bffssox.domain.ClientResponseException;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BffSessionManagerImplTest {

    private BffSessionManagerImpl bffSessionManager;

    @Mock
    private AuthUserClient authUserClient;

    @Mock
    private BffSessionStore bffSessionStore;

    @Mock
    private BffSessionProperties bffSessionProperties;

    @Mock
    private SessionIdGenerator sessionIdGenerator;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Clock clock;

    @BeforeEach
    void setUp() {
        this.bffSessionManager = new BffSessionManagerImpl(
                this.authUserClient,
                this.bffSessionStore,
                this.bffSessionProperties,
                this.sessionIdGenerator,
                this.objectMapper,
                this.clock
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.authUserClient,
                this.bffSessionStore,
                this.bffSessionProperties,
                this.sessionIdGenerator,
                this.objectMapper,
                this.clock
        );
    }

    @Test
    void givenValidLoginRequest_whenCreateSession_thenPersistSessionAndReturnSessionResponse() throws Exception {
        //given
        final Instant now = Instant.parse("2026-02-23T12:00:00Z");
        final UUID siteId = UUID.fromString("99970811-be3e-4a15-917b-e921f4069fba");
        final String claimsPayload = this.getClaimsPayload(1_772_153_200L, siteId);
        final String accessToken = this.getJwt(claimsPayload);
        final JsonNode claimsNode = this.readJson(claimsPayload);
        final LoginRequest request = this.getLoginRequest(siteId);

        when(this.clock.instant()).thenReturn(now);
        when(this.bffSessionProperties.getAbsoluteTimeoutSeconds()).thenReturn(1_200L);
        when(this.bffSessionProperties.getIdleTimeoutSeconds()).thenReturn(86_400L);
        when(this.authUserClient.login(any(LoginRequest.class))).thenReturn(LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken("refresh-token")
                .build());
        when(this.objectMapper.readTree(claimsPayload)).thenReturn(claimsNode);
        when(this.sessionIdGenerator.newCsrfToken()).thenReturn("csrf-token");
        when(this.bffSessionStore.create(any(BffSession.class))).thenReturn("session-id");

        //when
        final BffLoginSessionResult actual = this.bffSessionManager.createSession(request, "Mozilla", "10.0.0.5");

        //then
        assertThat(actual.getSessionId()).isEqualTo("session-id");
        assertThat(actual.getCsrfToken()).isEqualTo("csrf-token");
        assertThat(actual.getResponse().getAuthenticated()).isEqualTo(Boolean.TRUE);
        assertThat(actual.getResponse().getUser().getId()).isEqualTo("user-1");
        assertThat(actual.getResponse().getUser().getEmail()).isEqualTo("user@sitionix.com");
        assertThat(actual.getResponse().getUser().getRole()).isEqualTo("SUPER_ADMIN");
        assertThat(actual.getResponse().getUser().getSiteId()).isEqualTo(siteId);
        assertThat(actual.getResponse().getIdleTimeoutSeconds()).isEqualTo(86_400L);
        assertThat(actual.getResponse().getExpiresAt()).isEqualTo(now.plusSeconds(1_200L));

        final ArgumentCaptor<LoginRequest> loginRequestCaptor = ArgumentCaptor.forClass(LoginRequest.class);
        verify(this.authUserClient).login(loginRequestCaptor.capture());
        final LoginRequest authLoginRequest = loginRequestCaptor.getValue();
        assertThat(authLoginRequest.getEmail()).isEqualTo("user@sitionix.com");
        assertThat(authLoginRequest.getPassword()).isEqualTo("password");
        assertThat(authLoginRequest.getSiteId()).isEqualTo(siteId);
        assertThat(authLoginRequest.getUserAgent()).isEqualTo("Mozilla");
        assertThat(authLoginRequest.getSessionSourceId()).isNotBlank();

        final ArgumentCaptor<BffSession> sessionCaptor = ArgumentCaptor.forClass(BffSession.class);
        verify(this.bffSessionStore).create(sessionCaptor.capture());
        final BffSession persistedSession = sessionCaptor.getValue();
        assertThat(persistedSession.getAuthSessionSourceId()).isEqualTo(authLoginRequest.getSessionSourceId());
        assertThat(persistedSession.getAccessToken()).isEqualTo(accessToken);
        assertThat(persistedSession.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(persistedSession.getCsrfToken()).isEqualTo("csrf-token");

        verify(this.objectMapper).readTree(claimsPayload);
        verify(this.clock).instant();
        verify(this.sessionIdGenerator).newCsrfToken();
        verify(this.bffSessionProperties, times(2)).getAbsoluteTimeoutSeconds();
        verify(this.bffSessionProperties).getIdleTimeoutSeconds();
    }

    @Test
    void givenIdleExpiredSession_whenResolveSession_thenInvalidateAndReturnEmpty() {
        //given
        final Instant now = Instant.parse("2026-02-23T12:00:00Z");
        final BffSession session = this.getSession(
                now.minusSeconds(120L),
                now.minusSeconds(360L),
                now.plusSeconds(600L),
                now.plusSeconds(600L)
        );

        when(this.bffSessionStore.get("session-id")).thenReturn(Optional.of(session));
        when(this.bffSessionProperties.getIdleTimeoutSeconds()).thenReturn(60L);
        when(this.clock.instant()).thenReturn(now);

        //when
        final Optional<BffResolvedSession> actual = this.bffSessionManager.resolveSession("session-id", "Mozilla", "10.0.0.5");

        //then
        assertThat(actual).isEmpty();

        verify(this.bffSessionStore).get("session-id");
        verify(this.bffSessionStore).delete("session-id");
        verify(this.bffSessionProperties).getIdleTimeoutSeconds();
        verify(this.clock).instant();
    }

    @Test
    void givenSessionRequiresRotationAndTouch_whenResolveSession_thenRotateAndTouchSession() {
        //given
        final Instant now = Instant.parse("2026-02-23T12:00:00Z");
        final BffSession session = this.getSession(
                now.minusSeconds(120L),
                now.minusSeconds(500L),
                now.plusSeconds(600L),
                now.plusSeconds(600L)
        );
        final BffSession rotatedSession = session.toBuilder()
                .lastRotatedAt(now)
                .build();

        when(this.bffSessionStore.get("session-id"))
                .thenReturn(Optional.of(session), Optional.of(session));
        when(this.bffSessionStore.rotate("session-id", now)).thenReturn("session-id-2");
        when(this.bffSessionStore.get("session-id-2")).thenReturn(Optional.of(rotatedSession));
        when(this.bffSessionProperties.getIdleTimeoutSeconds()).thenReturn(300L);
        when(this.bffSessionProperties.getRefreshSkewSeconds()).thenReturn(60L);
        when(this.bffSessionProperties.getRotationIntervalSeconds()).thenReturn(300L);
        when(this.bffSessionProperties.getTouchThrottleSeconds()).thenReturn(30L);
        when(this.clock.instant()).thenReturn(now, now);

        //when
        final Optional<BffResolvedSession> actual = this.bffSessionManager.resolveSession("session-id", "Mozilla", "10.0.0.5");

        //then
        assertThat(actual).isPresent();
        assertThat(actual.get().getSessionId()).isEqualTo("session-id-2");
        assertThat(actual.get().getPreviousSessionId()).isEqualTo("session-id");
        assertThat(actual.get().getSession().getLastUsedAt()).isEqualTo(now);

        verify(this.bffSessionStore, times(2)).get("session-id");
        verify(this.bffSessionStore).rotate("session-id", now);
        verify(this.bffSessionStore).get("session-id-2");
        verify(this.bffSessionStore).touch("session-id-2", now);

        final ArgumentCaptor<BffSession> updatedSessionCaptor = ArgumentCaptor.forClass(BffSession.class);
        verify(this.bffSessionStore).update(eq("session-id-2"), updatedSessionCaptor.capture());
        assertThat(updatedSessionCaptor.getValue().getLastUsedAt()).isEqualTo(now);

        verify(this.bffSessionProperties).getIdleTimeoutSeconds();
        verify(this.bffSessionProperties).getRefreshSkewSeconds();
        verify(this.bffSessionProperties).getRotationIntervalSeconds();
        verify(this.bffSessionProperties).getTouchThrottleSeconds();
        verify(this.clock, times(2)).instant();
    }

    @Test
    void givenRefreshUnauthorized_whenRefreshSession_thenInvalidateAndReturnEmpty() {
        //given
        final Instant now = Instant.parse("2026-02-23T12:00:00Z");
        final BffSession session = this.getSession(
                now.minusSeconds(10L),
                now.minusSeconds(10L),
                now.plusSeconds(15L),
                now.plusSeconds(600L)
        );

        when(this.bffSessionStore.get("session-id"))
                .thenReturn(Optional.of(session), Optional.of(session));
        when(this.bffSessionProperties.getRefreshSkewSeconds()).thenReturn(60L);
        when(this.bffSessionProperties.getIdleTimeoutSeconds()).thenReturn(300L);
        when(this.clock.instant()).thenReturn(now, now);
        when(this.authUserClient.refreshAccessToken(any(RefreshAccessTokenRequest.class)))
                .thenThrow(new ClientResponseException(
                        401,
                        "{\"error\":\"unauthorized\"}",
                        Collections.emptyMap(),
                        null
                ));

        //when
        final Optional<BffResolvedSession> actual = this.bffSessionManager.refreshSession("session-id", false);

        //then
        assertThat(actual).isEmpty();

        final ArgumentCaptor<RefreshAccessTokenRequest> requestCaptor = ArgumentCaptor.forClass(RefreshAccessTokenRequest.class);
        verify(this.authUserClient).refreshAccessToken(requestCaptor.capture());
        assertThat(requestCaptor.getValue().getRefreshToken()).isEqualTo("refresh-token");
        assertThat(requestCaptor.getValue().getSessionSourceId()).isEqualTo("source-id");

        verify(this.bffSessionStore, times(2)).get("session-id");
        verify(this.bffSessionStore).delete("session-id");
        verify(this.bffSessionProperties, times(2)).getRefreshSkewSeconds();
        verify(this.bffSessionProperties).getIdleTimeoutSeconds();
        verify(this.clock, times(2)).instant();
    }

    private LoginRequest getLoginRequest(final UUID siteId) {
        return LoginRequest.builder()
                .email("user@sitionix.com")
                .password("password")
                .siteId(siteId)
                .build();
    }

    private BffSession getSession(final Instant lastUsedAt,
                                  final Instant lastRotatedAt,
                                  final Instant accessTokenExpiresAt,
                                  final Instant maxExpiresAt) {
        return BffSession.builder()
                .userId("user-1")
                .email("user@sitionix.com")
                .role("SUPER_ADMIN")
                .siteId(UUID.fromString("99970811-be3e-4a15-917b-e921f4069fba"))
                .authSessionSourceId("source-id")
                .accessToken("access-token")
                .accessTokenExpiresAt(accessTokenExpiresAt)
                .refreshToken("refresh-token")
                .csrfToken("csrf-token")
                .createdAt(lastUsedAt)
                .lastUsedAt(lastUsedAt)
                .lastRotatedAt(lastRotatedAt)
                .maxExpiresAt(maxExpiresAt)
                .build();
    }

    private String getClaimsPayload(final long exp, final UUID siteId) {
        return "{\"sub\":\"user-1\",\"email\":\"user@sitionix.com\",\"role\":\"SUPER_ADMIN\",\"siteId\":\""
                + siteId + "\",\"exp\":" + exp + "}";
    }

    private String getJwt(final String payload) {
        final String encodedPayload = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return "header." + encodedPayload + ".signature";
    }

    private JsonNode readJson(final String payload) throws Exception {
        return new ObjectMapper().readTree(payload);
    }
}
