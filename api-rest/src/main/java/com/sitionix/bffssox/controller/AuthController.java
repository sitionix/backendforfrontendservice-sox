package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AuthApi;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.mapper.EmailVerificationApiMapper;
import com.sitionix.bffssox.mapper.LoginUserApiMapper;
import com.sitionix.bffssox.mapper.RefreshAccessTokenApiMapper;
import com.sitionix.bffssox.usecase.LoginUser;
import com.sitionix.bffssox.usecase.RefreshAccessToken;
import com.sitionix.bffssox.usecase.VerifyEmail;
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

    private final EmailVerificationApiMapper emailVerificationApiMapper;

    private final VerifyEmail verifyEmail;

    private final RefreshAccessTokenApiMapper refreshAccessTokenApiMapper;

    private final RefreshAccessToken refreshAccessToken;

    @Override
    public ResponseEntity<LoginResponseDTO> login(@Valid final LoginRequestDTO loginRequestDTO) {
        final LoginRequest loginRequest = this.loginUserApiMapper.asLoginRequest(loginRequestDTO);

        final LoginResponse response = this.loginUser.execute(loginRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.loginUserApiMapper.asLoginResponseDTO(response));
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
}
