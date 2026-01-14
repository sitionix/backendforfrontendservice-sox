package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshAccessTokenRequest {

    private String refreshToken;

    private String sessionSourceId;
}
