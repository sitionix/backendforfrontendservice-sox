package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.UserApi;
import com.app_afesox.bffssox.api_first.dto.RegisterUserDTO;
import com.app_afesox.bffssox.api_first.dto.ResponseRegisterUserDTO;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import com.sitionix.bffssox.mapper.RegisterUserApiMapper;
import com.sitionix.bffssox.usecase.RegisterUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final RegisterUserApiMapper registerUserApiMapper;

    private final RegisterUser registerUser;

    @Override
    public ResponseEntity<ResponseRegisterUserDTO> registerUser(@Valid final RegisterUserDTO registerUserDTO) {
        final RegisterUserRequest registerUserRequest =
                this.registerUserApiMapper.asRegisterUserRequest(registerUserDTO);

        final RegisterUserResponse response = this.registerUser.execute(registerUserRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.registerUserApiMapper.asResponseRegisterUserDto(response));
    }
}
