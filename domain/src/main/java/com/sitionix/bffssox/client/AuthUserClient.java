package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;

public interface AuthUserClient {

    LoginResponse login(LoginRequest request);

    EmailVerificationResponse verifyEmail(EmailVerificationRequest request);

}
