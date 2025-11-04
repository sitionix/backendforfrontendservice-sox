package com.sitionix.bffssox.client;

import com.app_afesox.athssox.client.api.AuthApi;
import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.mapper.LoginUserClientMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserClientImplTest {

    private AuthUserClient authUserClient;

    @Mock
    private AuthApi authApi;

    @Mock
    private LoginUserClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        this.authUserClient = new AuthUserClientImpl(this.authApi,
                this.clientMapper);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.authApi,
                this.clientMapper);
    }

    @Test
    void givenLoginRequest_whenLogin_thenReturnLoginResponse() {
        //given
        final LoginRequest request = mock(LoginRequest.class);
        final LoginResponse response = mock(LoginResponse.class);

        final LoginRequestDTO requestDTO = mock(LoginRequestDTO.class);
        final LoginResponseDTO responseDTO = mock(LoginResponseDTO.class);

        when(this.authApi.login(requestDTO)).thenReturn(responseDTO);

        when(this.clientMapper.asLoginRequestDto(request)).thenReturn(requestDTO);
        when(this.clientMapper.asLoginResponse(responseDTO)).thenReturn(response);

        //when
        final LoginResponse actual = this.authUserClient.login(request);

        //then
        assertThat(actual).isEqualTo(response);
    }
}