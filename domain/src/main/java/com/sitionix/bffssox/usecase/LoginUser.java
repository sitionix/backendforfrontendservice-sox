package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.BffLoginSessionResult;
import com.sitionix.bffssox.domain.LoginRequest;

public interface LoginUser {

    BffLoginSessionResult execute(LoginRequest request, String userAgent, String remoteAddress);
}
