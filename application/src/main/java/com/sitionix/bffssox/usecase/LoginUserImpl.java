package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserImpl implements LoginUser{

    private final AuthUserClient authUserClient;

    @Override
    public LoginResponse execute(final LoginRequest request) {
        return this.authUserClient.login(request);
    }
}
