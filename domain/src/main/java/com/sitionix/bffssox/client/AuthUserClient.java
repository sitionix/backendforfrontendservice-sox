package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;

public interface AuthUserClient {

    LoginResponse login(LoginRequest request);

    EmailVerificationResponse verifyEmail(EmailVerificationRequest request);

    ResendEmailVerificationResponse resendEmailVerification();

    RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request);

}
