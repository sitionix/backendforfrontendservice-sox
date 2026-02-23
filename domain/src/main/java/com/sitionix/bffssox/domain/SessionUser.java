package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SessionUser {

    private String id;

    private String email;

    private String role;

    private UUID siteId;
}
