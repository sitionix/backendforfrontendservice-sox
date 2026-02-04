package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResendEmailVerificationResponse {

    private String message;
}
