package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.UserClient;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserImpl implements RegisterUser {

    private final UserClient userClient;

    @Override
    public RegisterUserResponse execute(final RegisterUserRequest request) {
        return this.userClient.registerUser(request);
    }
}
