package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;

public interface AuthUserClient {

    LoginResponse login(LoginRequest request);

}
