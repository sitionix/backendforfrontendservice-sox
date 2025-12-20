package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;

public interface UserClient {

    RegisterUserResponse registerUser(RegisterUserRequest request);
}
