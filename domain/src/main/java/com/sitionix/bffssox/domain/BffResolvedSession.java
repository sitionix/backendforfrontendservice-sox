package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BffResolvedSession {

    private String sessionId;

    private String previousSessionId;

    private BffSession session;
}
