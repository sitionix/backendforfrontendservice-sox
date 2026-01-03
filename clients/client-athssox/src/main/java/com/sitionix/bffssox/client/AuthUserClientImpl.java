package com.sitionix.bffssox.client;

import com.app_afesox.athssox.client.api.AuthApi;
import com.app_afesox.athssox.client.dto.EmailVerificationDTO;
import com.app_afesox.athssox.client.dto.EmailVerificationResponseDTO;
import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.mapper.EmailVerificationClientMapper;
import com.sitionix.bffssox.mapper.LoginUserClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserClientImpl implements AuthUserClient {

    private final AuthApi authApi;

    private final LoginUserClientMapper clientMapper;

    private final EmailVerificationClientMapper emailVerificationClientMapper;

    private final ClientCallExecutor clientCallExecutor;

    @Override
    public LoginResponse login(final LoginRequest request) {
        final LoginRequestDTO requestDTO = this.clientMapper.asLoginRequestDto(request);
        final LoginResponseDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.authApi.login(requestDTO)
        );
        return this.clientMapper.asLoginResponse(responseDTO);
    }

    @Override
    public EmailVerificationResponse verifyEmail(final EmailVerificationRequest request) {
        final EmailVerificationDTO requestDTO = this.emailVerificationClientMapper.asEmailVerificationDto(request);
        final EmailVerificationResponseDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.authApi.verifyEmail(requestDTO)
        );
        return this.emailVerificationClientMapper.asEmailVerificationResponse(responseDTO);
    }
}
