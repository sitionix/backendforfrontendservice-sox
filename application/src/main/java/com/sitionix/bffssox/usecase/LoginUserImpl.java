package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.BffLoginSessionResult;
import com.sitionix.bffssox.domain.BffSessionManager;
import com.sitionix.bffssox.domain.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserImpl implements LoginUser {

    private final BffSessionManager bffSessionManager;

    @Override
    public BffLoginSessionResult execute(final LoginRequest request, final String userAgent, final String remoteAddress) {
        return this.bffSessionManager.createSession(request, userAgent, remoteAddress);
    }
}
