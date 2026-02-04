package com.sitionix.bffssox.domain;

import org.springframework.stereotype.Component;

@Component
public class UserAccessTokenContext {

    private final ThreadLocal<String> accessToken = new ThreadLocal<>();

    public void set(final String token) {
        this.accessToken.set(token);
    }

    public String get() {
        return this.accessToken.get();
    }

    public void clear() {
        this.accessToken.remove();
    }
}
