package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;

public interface VerifyEmail {

    EmailVerificationResponse execute(EmailVerificationRequest request);
}
