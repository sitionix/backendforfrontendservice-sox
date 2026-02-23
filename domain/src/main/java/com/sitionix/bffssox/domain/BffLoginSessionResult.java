package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BffLoginSessionResult {

    private String sessionId;

    private SessionResponse response;

    private String csrfToken;
}
