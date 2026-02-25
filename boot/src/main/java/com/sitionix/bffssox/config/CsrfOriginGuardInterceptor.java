package com.sitionix.bffssox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
public class CsrfOriginGuardInterceptor implements HandlerInterceptor {

    private static final Set<String> UNSAFE_METHODS = Set.of(
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name()
    );

    private final ObjectMapper objectMapper;
    private final CorsConfiguration corsConfiguration;

    public CsrfOriginGuardInterceptor(final CorsProps corsProps,
                                      final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.corsConfiguration = new CorsConfiguration();
        this.corsConfiguration.setAllowedOrigins(Objects.isNull(corsProps.allowedOrigins()) ? List.of() : corsProps.allowedOrigins());
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (!this.shouldEnforce(request)) {
            return true;
        }
        if (this.isAllowed(request)) {
            return true;
        }
        this.writeForbidden(request, response);
        return false;
    }

    private boolean shouldEnforce(final HttpServletRequest request) {
        return UNSAFE_METHODS.contains(request.getMethod());
    }

    private boolean isAllowed(final HttpServletRequest request) {
        final String originHeader = request.getHeader(HttpHeaders.ORIGIN);
        if (StringUtils.hasText(originHeader)) {
            return this.isAllowedOrigin(originHeader);
        }

        final String refererHeader = request.getHeader(HttpHeaders.REFERER);
        if (StringUtils.hasText(refererHeader)) {
            return this.isAllowedOrigin(this.extractOriginFromReferer(refererHeader));
        }

        return false;
    }

    private boolean isAllowedOrigin(final String origin) {
        final String canonicalOrigin = this.toCanonicalOrigin(origin);
        if (!StringUtils.hasText(canonicalOrigin)) {
            return false;
        }
        return StringUtils.hasText(this.corsConfiguration.checkOrigin(canonicalOrigin));
    }

    private String extractOriginFromReferer(final String referer) {
        return this.toCanonicalOrigin(referer);
    }

    private String toCanonicalOrigin(final String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        try {
            final var uri = UriComponentsBuilder.fromUriString(value.trim()).build().toUri();
            if (!StringUtils.hasText(uri.getScheme()) || !StringUtils.hasText(uri.getHost())) {
                return null;
            }

            final String scheme = uri.getScheme().toLowerCase(Locale.ROOT);
            final String host = uri.getHost().toLowerCase(Locale.ROOT);

            return UriComponentsBuilder.newInstance()
                    .scheme(scheme)
                    .host(host)
                    .port(uri.getPort() >= 0 ? uri.getPort() : null)
                    .build()
                    .toUriString();
        } catch (final RuntimeException ex) {
            return null;
        }
    }

    private void writeForbidden(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Map<String, String> body = new LinkedHashMap<>();
        body.put("code", "CSRF_ORIGIN");
        body.put("title", "Forbidden");
        body.put("details", "Invalid request origin");
        body.put("traceId", this.resolveTraceId(request));

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(this.objectMapper.writeValueAsString(body));
    }

    private String resolveTraceId(final HttpServletRequest request) {
        final String b3TraceId = request.getHeader("X-B3-TraceId");
        if (StringUtils.hasText(b3TraceId)) {
            return b3TraceId;
        }

        final String requestId = request.getHeader("X-Request-Id");
        if (StringUtils.hasText(requestId)) {
            return requestId;
        }

        final Object traceId = request.getAttribute("traceId");
        if (traceId instanceof String value && StringUtils.hasText(value)) {
            return value;
        }

        return UUID.randomUUID().toString();
    }
}
