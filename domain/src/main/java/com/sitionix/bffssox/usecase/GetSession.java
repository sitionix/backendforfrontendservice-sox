package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.BffResolvedSession;

import java.util.Optional;

public interface GetSession {

    Optional<BffResolvedSession> execute(String sessionId, String userAgent, String remoteAddress);
}
