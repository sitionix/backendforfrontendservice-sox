package com.sitionix.bffssox.client;

import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.app_afesox.athssox.client.api.AuthApi;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.mapper.LoginUserClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserClientImpl implements AuthUserClient {

    private final AuthApi authApi;

    private final LoginUserClientMapper clientMapper;

    private final ClientCallExecutor clientCallExecutor;

    @Override
    public LoginResponse login(final LoginRequest request) {
        final LoginRequestDTO requestDTO = this.clientMapper.asLoginRequestDto(request);
        final LoginResponseDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.authApi.login(requestDTO)
        );
        return this.clientMapper.asLoginResponse(responseDTO);
    }
}
