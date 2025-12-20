package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;

public interface RegisterUser {

    RegisterUserResponse execute(RegisterUserRequest request);
}
