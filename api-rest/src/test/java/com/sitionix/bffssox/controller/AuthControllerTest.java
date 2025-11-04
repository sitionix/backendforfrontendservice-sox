package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.mapper.LoginUserApiMapper;
import com.sitionix.bffssox.usecase.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @BeforeEach
    void setUp() {
        this.authController = new AuthController(this.mapper, this.loginUser);
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

        //when
        final ResponseEntity<LoginResponseDTO> actual = this.authController.login(loginRequestDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO));

        verify(this.mapper).asLoginRequest(loginRequestDTO);
        verify(this.loginUser).execute(loginRequest);
        verify(this.mapper).asLoginResponseDTO(loginResponse);
    }
}