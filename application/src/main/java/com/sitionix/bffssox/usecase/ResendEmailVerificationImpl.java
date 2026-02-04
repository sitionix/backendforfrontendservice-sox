package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResendEmailVerificationImpl implements ResendEmailVerification {

    private final AuthUserClient authUserClient;

    @Override
    public ResendEmailVerificationResponse execute() {
        return this.authUserClient.resendEmailVerification();
    }
}
