package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;

public interface LoginUser {

    LoginResponse execute(LoginRequest request);
}
