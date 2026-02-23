package com.sitionix.bffssox.session;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SessionIdGenerator {

    private static final int SESSION_ID_BYTES = 32;

    private static final int CSRF_TOKEN_BYTES = 24;

    private final SecureRandom secureRandom = new SecureRandom();

    public String newSessionId() {
        return this.generate(SESSION_ID_BYTES);
    }

    public String newCsrfToken() {
        return this.generate(CSRF_TOKEN_BYTES);
    }

    private String generate(final int byteSize) {
        final byte[] bytes = new byte[byteSize];
        this.secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
