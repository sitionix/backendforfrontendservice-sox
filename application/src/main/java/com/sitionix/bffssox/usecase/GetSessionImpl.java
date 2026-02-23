package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetSessionImpl implements GetSession {

    private final BffSessionManager bffSessionManager;

    @Override
    public Optional<BffResolvedSession> execute(final String sessionId, final String userAgent, final String remoteAddress) {
        return this.bffSessionManager.resolveSession(sessionId, userAgent, remoteAddress);
    }
}
