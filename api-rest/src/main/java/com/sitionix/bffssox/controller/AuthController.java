package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AuthApi;
import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.mapper.LoginUserApiMapper;
import com.sitionix.bffssox.usecase.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final LoginUserApiMapper loginUserApiMapper;

    private final LoginUser loginUser;

    @Override
    public ResponseEntity<LoginResponseDTO> login(@Valid final LoginRequestDTO loginRequestDTO) {
        final LoginRequest loginRequest = this.loginUserApiMapper.asLoginRequest(loginRequestDTO);

        final LoginResponse response = this.loginUser.execute(loginRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.loginUserApiMapper.asLoginResponseDTO(response));
    }
}
