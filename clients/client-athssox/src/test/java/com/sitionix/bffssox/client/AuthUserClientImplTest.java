package com.sitionix.bffssox.client;

import com.app_afesox.athssox.client.api.AuthApi;
import com.app_afesox.athssox.client.dto.EmailVerificationDTO;
import com.app_afesox.athssox.client.dto.EmailVerificationResponseDTO;
import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.athssox.client.dto.ResendEmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import com.sitionix.bffssox.mapper.EmailVerificationClientMapper;
import com.sitionix.bffssox.mapper.LoginUserClientMapper;
import com.sitionix.bffssox.mapper.RefreshAccessTokenClientMapper;
import com.sitionix.bffssox.mapper.ResendEmailVerificationClientMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserClientImplTest {

    private AuthUserClient authUserClient;

    @Mock
    private AuthApi authApi;

    @Mock
    private LoginUserClientMapper clientMapper;

    @Mock
    private EmailVerificationClientMapper emailVerificationClientMapper;

    @Mock
    private RefreshAccessTokenClientMapper refreshAccessTokenClientMapper;

    @Mock
    private ResendEmailVerificationClientMapper resendEmailVerificationClientMapper;

    @Mock
    private ClientCallExecutor clientCallExecutor;

    @BeforeEach
    void setUp() {
        this.authUserClient = new AuthUserClientImpl(this.authApi,
                this.clientMapper,
                this.emailVerificationClientMapper,
                this.refreshAccessTokenClientMapper,
                this.resendEmailVerificationClientMapper,
                this.clientCallExecutor);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.clientMapper,
                this.emailVerificationClientMapper,
                this.refreshAccessTokenClientMapper,
                this.resendEmailVerificationClientMapper,
                this.authApi,
                this.clientCallExecutor);
    }

    @Test
    void givenLoginRequest_whenLogin_thenReturnLoginResponse() throws Exception {
        //given
        final LoginRequest request = mock(LoginRequest.class);
        final LoginResponse response = mock(LoginResponse.class);

        final LoginRequestDTO requestDTO = mock(LoginRequestDTO.class);
        final LoginResponseDTO responseDTO = mock(LoginResponseDTO.class);

        when(this.clientMapper.asLoginRequestDto(request)).thenReturn(requestDTO);
        when(this.clientMapper.asLoginResponse(responseDTO)).thenReturn(response);
        when(this.clientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<LoginResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.authApi.login(requestDTO)).thenReturn(responseDTO);

        //when
        final LoginResponse actual = this.authUserClient.login(request);

        //then
        assertThat(actual).isEqualTo(response);

        verify(this.clientMapper).asLoginRequestDto(request);
        verify(this.clientMapper).asLoginResponse(responseDTO);
        verify(this.clientCallExecutor).execute(any());
        verify(this.authApi).login(requestDTO);
    }

    @Test
    void givenEmailVerificationRequest_whenVerifyEmail_thenReturnEmailVerificationResponse() throws Exception {
        //given
        final EmailVerificationRequest request = mock(EmailVerificationRequest.class);
        final EmailVerificationResponse response = mock(EmailVerificationResponse.class);

        final EmailVerificationDTO requestDTO = mock(EmailVerificationDTO.class);
        final EmailVerificationResponseDTO responseDTO = mock(EmailVerificationResponseDTO.class);

        when(this.emailVerificationClientMapper.asEmailVerificationDto(request)).thenReturn(requestDTO);
        when(this.emailVerificationClientMapper.asEmailVerificationResponse(responseDTO)).thenReturn(response);
        when(this.clientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<EmailVerificationResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.authApi.verifyEmail(requestDTO)).thenReturn(responseDTO);

        //when
        final EmailVerificationResponse actual = this.authUserClient.verifyEmail(request);

        //then
        assertThat(actual).isEqualTo(response);

        verify(this.emailVerificationClientMapper).asEmailVerificationDto(request);
        verify(this.emailVerificationClientMapper).asEmailVerificationResponse(responseDTO);
        verify(this.clientCallExecutor).execute(any());
        verify(this.authApi).verifyEmail(requestDTO);
    }

    @Test
    void givenRefreshAccessTokenRequest_whenRefreshAccessToken_thenReturnRefreshAccessTokenResponse() throws Exception {
        //given
        final RefreshAccessTokenRequest request = mock(RefreshAccessTokenRequest.class);
        final RefreshAccessTokenResponse response = mock(RefreshAccessTokenResponse.class);

        final RefreshAccessTokenRequestDTO requestDTO = mock(RefreshAccessTokenRequestDTO.class);
        final RefreshAccessTokenResponseDTO responseDTO = mock(RefreshAccessTokenResponseDTO.class);

        when(this.refreshAccessTokenClientMapper.asRefreshAccessTokenRequestDto(request)).thenReturn(requestDTO);
        when(this.refreshAccessTokenClientMapper.asRefreshAccessTokenResponse(responseDTO)).thenReturn(response);
        when(this.clientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<RefreshAccessTokenResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.authApi.refreshAccessToken(requestDTO)).thenReturn(responseDTO);

        //when
        final RefreshAccessTokenResponse actual = this.authUserClient.refreshAccessToken(request);

        //then
        assertThat(actual).isEqualTo(response);

        verify(this.refreshAccessTokenClientMapper).asRefreshAccessTokenRequestDto(request);
        verify(this.refreshAccessTokenClientMapper).asRefreshAccessTokenResponse(responseDTO);
        verify(this.clientCallExecutor).execute(any());
        verify(this.authApi).refreshAccessToken(requestDTO);
    }

    @Test
    void givenResendEmailVerificationRequest_whenResendEmailVerification_thenReturnResendEmailVerificationResponse() throws Exception {
        //given
        final ResendEmailVerificationResponse response = mock(ResendEmailVerificationResponse.class);
        final ResendEmailVerificationResponseDTO responseDTO = mock(ResendEmailVerificationResponseDTO.class);

        when(this.resendEmailVerificationClientMapper.asResendEmailVerificationResponse(responseDTO))
                .thenReturn(response);
        when(this.clientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<ResendEmailVerificationResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.authApi.resendEmailVerification(any())).thenReturn(responseDTO);

        //when
        final ResendEmailVerificationResponse actual = this.authUserClient.resendEmailVerification();

        //then
        assertThat(actual).isEqualTo(response);

        verify(this.resendEmailVerificationClientMapper).asResendEmailVerificationResponse(responseDTO);
        verify(this.clientCallExecutor).execute(any());
        verify(this.authApi).resendEmailVerification(any());
    }
}
