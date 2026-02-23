package com.sitionix.bffssox.security;

import com.sitionix.bffssox.domain.BffSessionProperties;
import com.sitionix.bffssox.domain.SessionCookieManager;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class SessionCookieManagerImpl implements SessionCookieManager {

    private final BffSessionProperties properties;

    public SessionCookieManagerImpl(final BffSessionProperties properties) {
        this.properties = properties;
    }

    @Override
    public String sessionCookieName() {
        return this.properties.getCookieName();
    }

    @Override
    public String csrfCookieName() {
        return this.properties.getCsrf().getCookieName();
    }

    @Override
    public String createSessionCookie(final String sessionId) {
        return ResponseCookie.from(this.properties.getCookieName(), sessionId)
                .httpOnly(this.properties.isHttpOnly())
                .secure(this.properties.isSecure())
                .sameSite(this.properties.getSameSite())
                .path("/")
                .maxAge(Duration.ofSeconds(this.properties.getAbsoluteTimeoutSeconds()))
                .build()
                .toString();
    }

    @Override
    public String clearSessionCookie() {
        return ResponseCookie.from(this.properties.getCookieName(), "")
                .httpOnly(this.properties.isHttpOnly())
                .secure(this.properties.isSecure())
                .sameSite(this.properties.getSameSite())
                .path("/")
                .maxAge(Duration.ZERO)
                .build()
                .toString();
    }

    @Override
    public String createCsrfCookie(final String csrfToken) {
        return ResponseCookie.from(this.properties.getCsrf().getCookieName(), csrfToken)
                .httpOnly(false)
                .secure(this.properties.isSecure())
                .sameSite(this.properties.getSameSite())
                .path("/")
                .maxAge(Duration.ofSeconds(this.properties.getAbsoluteTimeoutSeconds()))
                .build()
                .toString();
    }

    @Override
    public String clearCsrfCookie() {
        return ResponseCookie.from(this.properties.getCsrf().getCookieName(), "")
                .httpOnly(false)
                .secure(this.properties.isSecure())
                .sameSite(this.properties.getSameSite())
                .path("/")
                .maxAge(Duration.ZERO)
                .build()
                .toString();
    }
}
