package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AuthApi;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ResendEmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.BffLoginSessionResult;
import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.BffSessionProperties;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import com.sitionix.bffssox.domain.SessionResponse;
import com.sitionix.bffssox.domain.SessionCookieManager;
import com.sitionix.bffssox.domain.SessionUser;
import com.sitionix.bffssox.mapper.EmailVerificationApiMapper;
import com.sitionix.bffssox.mapper.LoginUserApiMapper;
import com.sitionix.bffssox.mapper.RefreshAccessTokenApiMapper;
import com.sitionix.bffssox.mapper.ResendEmailVerificationApiMapper;
import com.sitionix.bffssox.usecase.GetSession;
import com.sitionix.bffssox.usecase.LoginUser;
import com.sitionix.bffssox.usecase.RefreshAccessToken;
import com.sitionix.bffssox.usecase.ResendEmailVerification;
import com.sitionix.bffssox.usecase.VerifyEmail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final LoginUserApiMapper loginUserApiMapper;

    private final LoginUser loginUser;

    private final GetSession getSession;

    private final EmailVerificationApiMapper emailVerificationApiMapper;

    private final VerifyEmail verifyEmail;

    private final RefreshAccessTokenApiMapper refreshAccessTokenApiMapper;

    private final RefreshAccessToken refreshAccessToken;

    private final ResendEmailVerificationApiMapper resendEmailVerificationApiMapper;

    private final ResendEmailVerification resendEmailVerification;

    private final SessionCookieManager sessionCookieManager;

    private final BffSessionProperties bffSessionProperties;

    @Override
    public ResponseEntity<LoginResponseDTO> getSession() {
        final String sessionId = this.readSessionId().orElse(null);
        final Optional<BffResolvedSession> resolvedSession = this.getSession.execute(
                sessionId,
                this.readUserAgent(),
                this.readRemoteAddress()
        );
        if (resolvedSession.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, this.sessionCookieManager.clearSessionCookie())
                    .header(HttpHeaders.SET_COOKIE, this.sessionCookieManager.clearCsrfCookie())
                    .body(new LoginResponseDTO().authenticated(Boolean.FALSE));
        }
        final BffResolvedSession session = resolvedSession.get();
        final SessionResponse response = this.toLoginResponse(session);
        final ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, this.sessionCookieManager.createCsrfCookie(session.getSession().getCsrfToken()));
        if (session.getPreviousSessionId() != null) {
            responseBuilder.header(HttpHeaders.SET_COOKIE, this.sessionCookieManager.createSessionCookie(session.getSessionId()));
        }
        return responseBuilder.body(this.loginUserApiMapper.asLoginResponseDTO(response));
    }

    @Override
    public ResponseEntity<LoginResponseDTO> login(@Valid final LoginRequestDTO loginRequestDTO) {
        final LoginRequest loginRequest = this.loginUserApiMapper.asLoginRequest(loginRequestDTO);
        final BffLoginSessionResult response = this.loginUser.execute(
                loginRequest,
                this.readUserAgent(),
                this.readRemoteAddress()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, this.sessionCookieManager.createSessionCookie(response.getSessionId()))
                .header(HttpHeaders.SET_COOKIE, this.sessionCookieManager.createCsrfCookie(response.getCsrfToken()))
                .body(this.loginUserApiMapper.asLoginResponseDTO(response.getResponse()));
    }

    @Override
    public ResponseEntity<EmailVerificationResponseDTO> verifyEmail(@Valid final EmailVerificationDTO emailVerificationDTO) {
        final EmailVerificationRequest request = this.emailVerificationApiMapper
                .asEmailVerificationRequest(emailVerificationDTO);

        final EmailVerificationResponse response = this.verifyEmail.execute(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.emailVerificationApiMapper.asEmailVerificationResponseDTO(response));
    }

    @Override
    public ResponseEntity<RefreshAccessTokenResponseDTO> refreshAccessToken(
            @Valid final RefreshAccessTokenRequestDTO refreshAccessTokenRequestDTO) {
        final RefreshAccessTokenRequest request = this.refreshAccessTokenApiMapper
                .asRefreshAccessTokenRequest(refreshAccessTokenRequestDTO);

        final RefreshAccessTokenResponse response = this.refreshAccessToken.execute(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.refreshAccessTokenApiMapper.asRefreshAccessTokenResponseDTO(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResendEmailVerificationResponseDTO> resendEmailVerification(final Object body) {
        final ResendEmailVerificationResponse response = this.resendEmailVerification.execute();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(this.resendEmailVerificationApiMapper.asResendEmailVerificationResponseDTO(response));
    }

    private SessionResponse toLoginResponse(final BffResolvedSession resolvedSession) {
        return SessionResponse.builder()
                .authenticated(Boolean.TRUE)
                .user(SessionUser.builder()
                        .id(resolvedSession.getSession().getUserId())
                        .email(resolvedSession.getSession().getEmail())
                        .role(resolvedSession.getSession().getRole())
                        .siteId(resolvedSession.getSession().getSiteId())
                        .build())
                .expiresAt(resolvedSession.getSession().getMaxExpiresAt())
                .idleTimeoutSeconds(this.bffSessionProperties.getIdleTimeoutSeconds())
                .build();
    }

    private Optional<String> readSessionId() {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null || attributes.getRequest().getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(attributes.getRequest().getCookies())
                .filter(cookie -> this.sessionCookieManager.sessionCookieName().equals(cookie.getName()))
                .map(jakarta.servlet.http.Cookie::getValue)
                .filter(value -> value != null && !value.isBlank())
                .findFirst();
    }

    private String readUserAgent() {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "";
        }
        final String value = attributes.getRequest().getHeader(HttpHeaders.USER_AGENT);
        return value == null ? "" : value;
    }

    private String readRemoteAddress() {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "";
        }
        final String value = attributes.getRequest().getRemoteAddr();
        return value == null ? "" : value;
    }
}
