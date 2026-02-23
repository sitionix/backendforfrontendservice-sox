package com.sitionix.bffssox.domain;

import java.time.Instant;
import java.util.Optional;

/**
 * Stores and manages BFF server-side sessions by session identifier.
 */
public interface BffSessionStore {

    /**
     * Creates a new session record and returns generated session id.
     *
     * @param session session payload.
     * @return generated session id.
     */
    String create(BffSession session);

    /**
     * Loads session by id.
     *
     * @param sessionId session id.
     * @return optional session.
     */
    Optional<BffSession> get(String sessionId);

    /**
     * Replaces stored session payload by id.
     *
     * @param sessionId session id.
     * @param session session payload.
     */
    void update(String sessionId, BffSession session);

    /**
     * Updates last-used timestamp for an existing session.
     *
     * @param sessionId session id.
     * @param now timestamp.
     */
    void touch(String sessionId, Instant now);

    /**
     * Rotates session id while keeping payload.
     *
     * @param sessionId current session id.
     * @param now rotation timestamp.
     * @return new session id.
     */
    String rotate(String sessionId, Instant now);

    /**
     * Deletes session by id.
     *
     * @param sessionId session id.
     */
    void delete(String sessionId);
}
