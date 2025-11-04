package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoginRequest {

    private String email;

    private String password;

    private UUID siteId;

    private String sessionSourceId;

    private String userAgent;
}
