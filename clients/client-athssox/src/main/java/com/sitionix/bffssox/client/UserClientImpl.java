package com.sitionix.bffssox.client;

import com.app_afesox.athssox.client.api.UserApi;
import com.app_afesox.athssox.client.dto.RegisterUserDTO;
import com.app_afesox.athssox.client.dto.ResponseRegisterUserDTO;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import com.sitionix.bffssox.mapper.RegisterUserClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private final UserApi userApi;

    private final RegisterUserClientMapper clientMapper;

    private final ClientCallExecutor clientCallExecutor;

    @Override
    public RegisterUserResponse registerUser(final RegisterUserRequest request) {
        final RegisterUserDTO requestDTO = this.clientMapper.asRegisterUserDto(request);
        final ResponseRegisterUserDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.userApi.registerUser(requestDTO)
        );
        return this.clientMapper.asRegisterUserResponse(responseDTO);
    }
}
