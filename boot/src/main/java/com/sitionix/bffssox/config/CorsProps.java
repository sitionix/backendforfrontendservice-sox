package com.sitionix.bffssox.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "sitionix.cors")
public record CorsProps(List<String> allowedOrigins) {}


