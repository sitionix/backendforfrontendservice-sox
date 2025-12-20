package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserRequest {

    private String email;

    private String password;

    private UUID siteId;

    private RegisterUserRole role;
}
