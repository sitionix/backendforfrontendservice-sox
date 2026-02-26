package com.sitionix.bffssox.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.refresh-cookie")
public record RefreshCookieProps(
        String name,
        boolean httpOnly,
        String sameSite,
        String path,
        boolean secure
) {
}
