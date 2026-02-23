package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ResendEmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.BffLoginSessionResult;
import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSession;
import com.sitionix.bffssox.domain.BffSessionProperties;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import com.sitionix.bffssox.domain.SessionCookieManager;
import com.sitionix.bffssox.domain.SessionResponse;
import com.sitionix.bffssox.mapper.EmailVerificationApiMapper;
import com.sitionix.bffssox.mapper.LoginUserApiMapper;
import com.sitionix.bffssox.mapper.RefreshAccessTokenApiMapper;
import com.sitionix.bffssox.mapper.ResendEmailVerificationApiMapper;
import com.sitionix.bffssox.usecase.GetSession;
import com.sitionix.bffssox.usecase.LoginUser;
import com.sitionix.bffssox.usecase.RefreshAccessToken;
import com.sitionix.bffssox.usecase.ResendEmailVerification;
import com.sitionix.bffssox.usecase.VerifyEmail;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private AuthController authController;

    @Mock
    private LoginUser loginUser;

    @Mock
    private LoginUserApiMapper mapper;

    @Mock
    private VerifyEmail verifyEmail;

    @Mock
    private GetSession getSession;

    @Mock
    private RefreshAccessToken refreshAccessToken;

    @Mock
    private EmailVerificationApiMapper emailVerificationApiMapper;

    @Mock
    private RefreshAccessTokenApiMapper refreshAccessTokenApiMapper;

    @Mock
    private ResendEmailVerificationApiMapper resendEmailVerificationApiMapper;

    @Mock
    private ResendEmailVerification resendEmailVerification;

    @Mock
    private SessionCookieManager sessionCookieManager;

    @Mock
    private BffSessionProperties bffSessionProperties;

    @BeforeEach
    void setUp() {
        this.authController = new AuthController(this.mapper, this.loginUser, this.getSession,
                this.emailVerificationApiMapper, this.verifyEmail,
                this.refreshAccessTokenApiMapper, this.refreshAccessToken,
                this.resendEmailVerificationApiMapper, this.resendEmailVerification,
                this.sessionCookieManager, this.bffSessionProperties);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
        verifyNoMoreInteractions(this.loginUser,
                this.mapper,
                this.verifyEmail,
                this.getSession,
                this.refreshAccessToken,
                this.emailVerificationApiMapper,
                this.refreshAccessTokenApiMapper,
                this.resendEmailVerificationApiMapper,
                this.resendEmailVerification,
                this.sessionCookieManager,
                this.bffSessionProperties);
    }

    @Test
    void givenLoginRequestDto_whenLogin_thenReturnsResponseEntity() {
        //given
        final LoginRequestDTO loginRequestDTO = mock(LoginRequestDTO.class);
        final LoginResponseDTO loginResponseDTO = mock(LoginResponseDTO.class);
        final LoginRequest loginRequest = mock(LoginRequest.class);
        final SessionResponse sessionResponse = mock(SessionResponse.class);
        final BffLoginSessionResult loginSessionResult = mock(BffLoginSessionResult.class);

        when(this.mapper.asLoginRequest(loginRequestDTO)).thenReturn(loginRequest);
        when(this.loginUser.execute(loginRequest, "", "")).thenReturn(loginSessionResult);
        when(loginSessionResult.getResponse()).thenReturn(sessionResponse);
        when(loginSessionResult.getSessionId()).thenReturn("session-id");
        when(loginSessionResult.getCsrfToken()).thenReturn("csrf-token");
        when(this.sessionCookieManager.createSessionCookie("session-id")).thenReturn("session-cookie");
        when(this.sessionCookieManager.createCsrfCookie("csrf-token")).thenReturn("csrf-cookie");
        when(this.mapper.asLoginResponseDTO(sessionResponse)).thenReturn(loginResponseDTO);

        //when
        final ResponseEntity<LoginResponseDTO> actual = this.authController.login(loginRequestDTO);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isEqualTo(loginResponseDTO);
        assertThat(actual.getHeaders().get(HttpHeaders.SET_COOKIE)).containsExactly("session-cookie", "csrf-cookie");

        verify(this.mapper).asLoginRequest(loginRequestDTO);
        verify(this.loginUser).execute(loginRequest, "", "");
        verify(loginSessionResult).getSessionId();
        verify(loginSessionResult).getCsrfToken();
        verify(loginSessionResult).getResponse();
        verify(this.sessionCookieManager).createSessionCookie("session-id");
        verify(this.sessionCookieManager).createCsrfCookie("csrf-token");
        verify(this.mapper).asLoginResponseDTO(sessionResponse);
    }

    @Test
    void givenValidSessionCookie_whenGetSession_thenReturnsResolvedSession() {
        //given
        final LoginResponseDTO loginResponseDTO = mock(LoginResponseDTO.class);
        final BffResolvedSession resolvedSession = this.getResolvedSession();
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie("__Host-admin_session", "session-id"));
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla");
        request.setRemoteAddr("10.0.0.5");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(this.sessionCookieManager.sessionCookieName()).thenReturn("__Host-admin_session");
        when(this.getSession.execute("session-id", "Mozilla", "10.0.0.5"))
                .thenReturn(Optional.of(resolvedSession));
        when(this.sessionCookieManager.createCsrfCookie("csrf-token")).thenReturn("csrf-cookie");
        when(this.mapper.asLoginResponseDTO(any(SessionResponse.class))).thenReturn(loginResponseDTO);
        when(this.bffSessionProperties.getIdleTimeoutSeconds()).thenReturn(86_400L);

        //when
        final ResponseEntity<LoginResponseDTO> actual = this.authController.getSession();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isEqualTo(loginResponseDTO);
        assertThat(actual.getHeaders().get(HttpHeaders.SET_COOKIE)).containsExactly("csrf-cookie");

        verify(this.sessionCookieManager).sessionCookieName();
        verify(this.getSession).execute("session-id", "Mozilla", "10.0.0.5");
        verify(this.bffSessionProperties).getIdleTimeoutSeconds();
        verify(this.sessionCookieManager).createCsrfCookie("csrf-token");
        verify(this.mapper).asLoginResponseDTO(any(SessionResponse.class));
    }

    @Test
    void givenNoResolvedSession_whenGetSession_thenReturnsUnauthorizedAndClearsCookies() {
        //given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie("__Host-admin_session", "session-id"));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(this.sessionCookieManager.sessionCookieName()).thenReturn("__Host-admin_session");
        when(this.getSession.execute("session-id", "", "127.0.0.1")).thenReturn(Optional.empty());
        when(this.sessionCookieManager.clearSessionCookie()).thenReturn("clear-session-cookie");
        when(this.sessionCookieManager.clearCsrfCookie()).thenReturn("clear-csrf-cookie");

        //when
        final ResponseEntity<LoginResponseDTO> actual = this.authController.getSession();

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(actual.getBody()).isEqualTo(new LoginResponseDTO().authenticated(Boolean.FALSE));
        assertThat(actual.getHeaders().get(HttpHeaders.SET_COOKIE))
                .containsExactly("clear-session-cookie", "clear-csrf-cookie");

        verify(this.sessionCookieManager).sessionCookieName();
        verify(this.getSession).execute("session-id", "", "127.0.0.1");
        verify(this.sessionCookieManager).clearSessionCookie();
        verify(this.sessionCookieManager).clearCsrfCookie();
    }

    @Test
    void givenEmailVerificationDto_whenVerifyEmail_thenReturnsResponseEntity() {
        //given
        final EmailVerificationDTO emailVerificationDTO = mock(EmailVerificationDTO.class);
        final EmailVerificationResponseDTO responseDTO = mock(EmailVerificationResponseDTO.class);

        final EmailVerificationRequest request = mock(EmailVerificationRequest.class);
        final EmailVerificationResponse response = mock(EmailVerificationResponse.class);

        when(this.verifyEmail.execute(request)).thenReturn(response);
        when(this.emailVerificationApiMapper.asEmailVerificationResponseDTO(response))
                .thenReturn(responseDTO);

        when(this.emailVerificationApiMapper.asEmailVerificationRequest(emailVerificationDTO))
                .thenReturn(request);

        //when
        final ResponseEntity<EmailVerificationResponseDTO> actual =
                this.authController.verifyEmail(emailVerificationDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.OK).body(responseDTO));

        verify(this.emailVerificationApiMapper).asEmailVerificationRequest(emailVerificationDTO);
        verify(this.verifyEmail).execute(request);
        verify(this.emailVerificationApiMapper).asEmailVerificationResponseDTO(response);
    }

    @Test
    void givenRefreshAccessTokenRequestDto_whenRefreshAccessToken_thenReturnsResponseEntity() {
        //given
        final RefreshAccessTokenRequestDTO requestDTO = mock(RefreshAccessTokenRequestDTO.class);
        final RefreshAccessTokenResponseDTO responseDTO = mock(RefreshAccessTokenResponseDTO.class);

        final RefreshAccessTokenRequest request = mock(RefreshAccessTokenRequest.class);
        final RefreshAccessTokenResponse response = mock(RefreshAccessTokenResponse.class);

        when(this.refreshAccessToken.execute(request)).thenReturn(response);
        when(this.refreshAccessTokenApiMapper.asRefreshAccessTokenResponseDTO(response))
                .thenReturn(responseDTO);

        when(this.refreshAccessTokenApiMapper.asRefreshAccessTokenRequest(requestDTO))
                .thenReturn(request);

        //when
        final ResponseEntity<RefreshAccessTokenResponseDTO> actual =
                this.authController.refreshAccessToken(requestDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.OK).body(responseDTO));

        verify(this.refreshAccessTokenApiMapper).asRefreshAccessTokenRequest(requestDTO);
        verify(this.refreshAccessToken).execute(request);
        verify(this.refreshAccessTokenApiMapper).asRefreshAccessTokenResponseDTO(response);
    }

    @Test
    void givenResendEmailVerification_whenResendEmailVerification_thenReturnsResponseEntity() {
        //given
        final Object body = new Object();
        final ResendEmailVerificationResponse response = mock(ResendEmailVerificationResponse.class);
        final ResendEmailVerificationResponseDTO responseDTO = mock(ResendEmailVerificationResponseDTO.class);

        when(this.resendEmailVerification.execute())
                .thenReturn(response);
        when(this.resendEmailVerificationApiMapper.asResendEmailVerificationResponseDTO(response))
                .thenReturn(responseDTO);

        //when
        final ResponseEntity<ResendEmailVerificationResponseDTO> actual =
                this.authController.resendEmailVerification(body);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO));

        verify(this.resendEmailVerification).execute();
        verify(this.resendEmailVerificationApiMapper).asResendEmailVerificationResponseDTO(response);
    }

    private BffResolvedSession getResolvedSession() {
        return BffResolvedSession.builder()
                .sessionId("session-id")
                .session(this.getSessionData())
                .build();
    }

    private BffSession getSessionData() {
        return BffSession.builder()
                .userId("user-id")
                .email("user@sitionix.com")
                .role("SUPER_ADMIN")
                .siteId(UUID.fromString("0bf022f8-6f26-4ea8-ad6f-d8f93d32f60d"))
                .csrfToken("csrf-token")
                .maxExpiresAt(Instant.parse("2030-01-01T00:00:00Z"))
                .build();
    }
}
