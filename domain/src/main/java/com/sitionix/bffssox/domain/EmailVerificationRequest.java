package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailVerificationRequest {

    private String token;

    private UUID siteId;
}
