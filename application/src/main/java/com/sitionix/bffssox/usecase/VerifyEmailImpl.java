package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyEmailImpl implements VerifyEmail {

    private final AuthUserClient authUserClient;

    @Override
    public EmailVerificationResponse execute(final EmailVerificationRequest request) {
        return this.authUserClient.verifyEmail(request);
    }
}
