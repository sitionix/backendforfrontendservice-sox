package com.sitionix.bffssox.domain;

/**
 * Produces HTTP Set-Cookie headers for BFF session and CSRF cookies.
 */
public interface SessionCookieManager {

    /**
     * @return session cookie name.
     */
    String sessionCookieName();

    /**
     * @return csrf cookie name.
     */
    String csrfCookieName();

    /**
     * Creates Set-Cookie header for active session.
     *
     * @param sessionId session id.
     * @return Set-Cookie header value.
     */
    String createSessionCookie(String sessionId);

    /**
     * Creates Set-Cookie header for clearing active session.
     *
     * @return Set-Cookie header value.
     */
    String clearSessionCookie();

    /**
     * Creates Set-Cookie header for csrf token.
     *
     * @param csrfToken csrf token.
     * @return Set-Cookie header value.
     */
    String createCsrfCookie(String csrfToken);

    /**
     * Creates Set-Cookie header to clear csrf token.
     *
     * @return Set-Cookie header value.
     */
    String clearCsrfCookie();
}
