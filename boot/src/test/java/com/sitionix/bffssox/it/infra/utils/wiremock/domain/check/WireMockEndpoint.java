package com.sitionix.bffssox.it.infra.utils.wiremock.domain.check;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum WireMockEndpoint {

    LOGIN_USER("/authsox/api/v1/auth/login");

    @Getter
    private final String path;

    public String resolve(final Map<String, ?> vars) {
        return PathTemplate.resolve(this.path, vars == null ? Map.of() : vars);
    }

    public String raw() {
        return PathTemplate.resolve(this.path, Map.of());
    }
}
