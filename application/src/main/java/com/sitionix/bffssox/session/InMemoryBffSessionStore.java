package com.sitionix.bffssox.session;

import com.sitionix.bffssox.domain.BffSession;
import com.sitionix.bffssox.domain.BffSessionException;
import com.sitionix.bffssox.domain.BffSessionStore;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class InMemoryBffSessionStore implements BffSessionStore {

    private final ConcurrentMap<String, BffSession> sessions = new ConcurrentHashMap<>();

    private final SessionIdGenerator sessionIdGenerator;

    public InMemoryBffSessionStore(final SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    @Override
    public String create(final BffSession session) {
        final String sessionId = this.sessionIdGenerator.newSessionId();
        this.sessions.put(sessionId, session);
        return sessionId;
    }

    @Override
    public Optional<BffSession> get(final String sessionId) {
        return Optional.ofNullable(this.sessions.get(sessionId));
    }

    @Override
    public void update(final String sessionId, final BffSession session) {
        this.sessions.put(sessionId, session);
    }

    @Override
    public void touch(final String sessionId, final Instant now) {
        this.sessions.computeIfPresent(sessionId, (key, session) -> session.toBuilder()
                .lastUsedAt(now)
                .build());
    }

    @Override
    public String rotate(final String sessionId, final Instant now) {
        final BffSession current = this.sessions.remove(sessionId);
        if (current == null) {
            throw new BffSessionException("Session is not found for rotation");
        }
        final String newSessionId = this.sessionIdGenerator.newSessionId();
        this.sessions.put(newSessionId, current.toBuilder()
                .lastRotatedAt(now)
                .build());
        return newSessionId;
    }

    @Override
    public void delete(final String sessionId) {
        this.sessions.remove(sessionId);
    }
}
