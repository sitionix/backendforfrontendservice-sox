package com.sitionix.bffssox.domain;

import java.util.Optional;

/**
 * Manages cookie-backed server-side sessions for BFF authenticated flows.
 */
public interface BffSessionManager {

    /**
     * Authenticates the user via auth-service and creates a BFF session.
     *
     * @param request login payload.
     * @param userAgent browser user-agent.
     * @param remoteAddress client address.
     * @return created session result.
     */
    BffLoginSessionResult createSession(LoginRequest request, String userAgent, String remoteAddress);

    /**
     * Resolves and validates a session from a cookie id.
     *
     * @param sessionId cookie session id.
     * @param userAgent browser user-agent.
     * @param remoteAddress client address.
     * @return active resolved session if available.
     */
    Optional<BffResolvedSession> resolveSession(String sessionId, String userAgent, String remoteAddress);

    /**
     * Refreshes access token for an existing session.
     *
     * @param sessionId session id.
     * @param forceRefresh true to force refresh even when access token is still valid.
     * @return refreshed resolved session if refresh succeeds.
     */
    Optional<BffResolvedSession> refreshSession(String sessionId, boolean forceRefresh);

    /**
     * Invalidates and removes a session.
     *
     * @param sessionId session id.
     */
    void invalidateSession(String sessionId);
}
