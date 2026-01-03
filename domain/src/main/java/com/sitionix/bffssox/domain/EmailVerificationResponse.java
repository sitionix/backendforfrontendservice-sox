package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailVerificationResponse {

    private String message;

    private EmailVerificationStatus status;
}
