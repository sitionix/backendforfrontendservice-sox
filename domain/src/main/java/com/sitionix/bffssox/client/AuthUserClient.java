package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;

public interface AuthUserClient {

    LoginResponse login(LoginRequest request);

    EmailVerificationResponse verifyEmail(EmailVerificationRequest request);

    RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request);

}
