package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.RegisterUserDTO;
import com.app_afesox.bffssox.api_first.dto.ResponseRegisterUserDTO;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import com.sitionix.bffssox.mapper.RegisterUserApiMapper;
import com.sitionix.bffssox.usecase.RegisterUser;
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
class UserControllerTest {

    private UserController userController;

    @Mock
    private RegisterUser registerUser;

    @Mock
    private RegisterUserApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.userController = new UserController(this.mapper, this.registerUser);
    }

    @Test
    void givenRegisterUserDto_whenRegisterUser_thenReturnsResponseEntity() {
        //given
        final RegisterUserDTO registerUserDTO = mock(RegisterUserDTO.class);
        final ResponseRegisterUserDTO responseRegisterUserDTO = mock(ResponseRegisterUserDTO.class);

        final RegisterUserRequest registerUserRequest = mock(RegisterUserRequest.class);
        final RegisterUserResponse registerUserResponse = mock(RegisterUserResponse.class);

        when(this.registerUser.execute(registerUserRequest)).thenReturn(registerUserResponse);
        when(this.mapper.asResponseRegisterUserDto(registerUserResponse)).thenReturn(responseRegisterUserDTO);
        when(this.mapper.asRegisterUserRequest(registerUserDTO)).thenReturn(registerUserRequest);

        //when
        final ResponseEntity<ResponseRegisterUserDTO> actual = this.userController.registerUser(registerUserDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.OK).body(responseRegisterUserDTO));

        verify(this.mapper).asRegisterUserRequest(registerUserDTO);
        verify(this.registerUser).execute(registerUserRequest);
        verify(this.mapper).asResponseRegisterUserDto(registerUserResponse);
    }
}
