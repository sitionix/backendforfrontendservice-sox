package com.sitionix.bffssox.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.rest.client.defaults")
public record ApiRestClientDefaultsProps(
        Duration connectTimeout,
        Duration readTimeout
) {
}
