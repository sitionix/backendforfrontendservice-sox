package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ResendEmailVerificationResponseDTO;
import com.sitionix.bffssox.config.RefreshCookieManager;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import com.sitionix.bffssox.mapper.EmailVerificationApiMapper;
import com.sitionix.bffssox.mapper.LoginUserApiMapper;
import com.sitionix.bffssox.mapper.RefreshAccessTokenApiMapper;
import com.sitionix.bffssox.mapper.ResendEmailVerificationApiMapper;
import com.sitionix.bffssox.usecase.LoginUser;
import com.sitionix.bffssox.usecase.RefreshAccessToken;
import com.sitionix.bffssox.usecase.ResendEmailVerification;
import com.sitionix.bffssox.usecase.VerifyEmail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private RefreshCookieManager refreshCookieManager;

    @BeforeEach
    void setUp() {
        this.authController = new AuthController(this.mapper, this.loginUser,
                this.emailVerificationApiMapper, this.verifyEmail,
                this.refreshAccessTokenApiMapper, this.refreshAccessToken,
                this.resendEmailVerificationApiMapper, this.resendEmailVerification,
                this.refreshCookieManager);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.loginUser,
                this.mapper,
                this.verifyEmail,
                this.refreshAccessToken,
                this.emailVerificationApiMapper,
                this.refreshAccessTokenApiMapper,
                this.resendEmailVerificationApiMapper,
                this.resendEmailVerification,
                this.refreshCookieManager);
    }

    @Test
    void givenLoginRequestDto_whenLogin_thenReturnsResponseEntity() {
        //given
        final LoginRequestDTO loginRequestDTO = mock(LoginRequestDTO.class);
        final LoginResponseDTO loginResponseDTO = mock(LoginResponseDTO.class);

        final LoginRequest loginRequest = mock(LoginRequest.class);
        final LoginResponse loginResponse = mock(LoginResponse.class);

        when(this.loginUser.execute(loginRequest)).thenReturn(loginResponse);
        when(this.mapper.asLoginResponseDTO(loginResponse)).thenReturn(loginResponseDTO);
        when(this.mapper.asLoginRequest(loginRequestDTO)).thenReturn(loginRequest);
        when(loginResponse.getRefreshToken()).thenReturn("refresh-token");
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "refresh-cookie-value");
        when(this.refreshCookieManager.buildRefreshCookieHeaders("refresh-token"))
                .thenReturn(headers);

        //when
        final ResponseEntity<LoginResponseDTO> actual = this.authController.login(loginRequestDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, "refresh-cookie-value")
                .body(loginResponseDTO));

        verify(this.mapper).asLoginRequest(loginRequestDTO);
        verify(this.loginUser).execute(loginRequest);
        verify(this.refreshCookieManager).buildRefreshCookieHeaders("refresh-token");
        verify(this.mapper).asLoginResponseDTO(loginResponse);
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
        final String refreshToken = "refreshToken";
        final RefreshAccessTokenRequestDTO requestDTO = mock(RefreshAccessTokenRequestDTO.class);
        final RefreshAccessTokenResponseDTO responseDTO = mock(RefreshAccessTokenResponseDTO.class);

        final RefreshAccessTokenRequest request = mock(RefreshAccessTokenRequest.class);
        final RefreshAccessTokenResponse response = mock(RefreshAccessTokenResponse.class);

        when(this.refreshAccessToken.execute(request)).thenReturn(response);
        when(this.refreshAccessTokenApiMapper.asRefreshAccessTokenResponseDTO(response))
                .thenReturn(responseDTO);

        when(this.refreshAccessTokenApiMapper.asRefreshAccessTokenRequest(refreshToken, requestDTO))
                .thenReturn(request);
        when(response.getRefreshToken()).thenReturn("next-refresh-token");
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "next-refresh-cookie-value");
        when(this.refreshCookieManager.buildRefreshCookieHeaders("next-refresh-token"))
                .thenReturn(headers);

        //when
        final ResponseEntity<RefreshAccessTokenResponseDTO> actual =
                this.authController.refreshAccessToken(refreshToken, requestDTO, "http://localhost:3000", null);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, "next-refresh-cookie-value")
                .body(responseDTO));

        verify(this.refreshAccessTokenApiMapper).asRefreshAccessTokenRequest(refreshToken, requestDTO);
        verify(this.refreshAccessToken).execute(request);
        verify(this.refreshCookieManager).buildRefreshCookieHeaders("next-refresh-token");
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
}
