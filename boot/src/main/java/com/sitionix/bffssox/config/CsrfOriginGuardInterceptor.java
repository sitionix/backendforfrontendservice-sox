package com.sitionix.bffssox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class CsrfOriginGuardInterceptor implements HandlerInterceptor {

    private static final Set<String> UNSAFE_METHODS = Set.of("POST", "PUT", "PATCH", "DELETE");

    private final CsrfOriginGuardProps props;
    private final ObjectMapper objectMapper;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final Set<String> allowedOrigins;

    public CsrfOriginGuardInterceptor(final CsrfOriginGuardProps props,
                                      final CorsProps corsProps,
                                      final ObjectMapper objectMapper) {
        this.props = props;
        this.objectMapper = objectMapper;
        this.allowedOrigins = this.normalizeAllowedOrigins(corsProps.allowedOrigins());
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
        if (!this.props.enabled()) {
            return false;
        }
        if (!UNSAFE_METHODS.contains(request.getMethod())) {
            return false;
        }
        final String path = this.getPathWithinApplication(request);
        return this.props.protectedPaths().stream()
                .anyMatch(pattern -> this.antPathMatcher.match(pattern, path));
    }

    private boolean isAllowed(final HttpServletRequest request) {
        if (this.allowedOrigins.isEmpty()) {
            return false;
        }

        final String originHeader = request.getHeader(HttpHeaders.ORIGIN);
        if (StringUtils.hasText(originHeader)) {
            final String normalizedOrigin = this.normalizeOrigin(originHeader);
            return StringUtils.hasText(normalizedOrigin) && this.allowedOrigins.contains(normalizedOrigin);
        }

        final String refererHeader = request.getHeader(HttpHeaders.REFERER);
        if (StringUtils.hasText(refererHeader)) {
            final String normalizedRefererOrigin = this.normalizeOrigin(refererHeader);
            return StringUtils.hasText(normalizedRefererOrigin) && this.allowedOrigins.contains(normalizedRefererOrigin);
        }

        return false;
    }

    private Set<String> normalizeAllowedOrigins(final List<String> configuredOrigins) {
        final Set<String> origins = new LinkedHashSet<>();
        if (configuredOrigins == null || configuredOrigins.isEmpty()) {
            return Collections.emptySet();
        }
        for (final String origin : configuredOrigins) {
            final String normalized = this.normalizeOrigin(origin);
            if (StringUtils.hasText(normalized)) {
                origins.add(normalized);
            }
        }
        return Collections.unmodifiableSet(origins);
    }

    private String normalizeOrigin(final String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            final URI uri = URI.create(value.trim());
            if (!StringUtils.hasText(uri.getScheme()) || !StringUtils.hasText(uri.getHost())) {
                return null;
            }
            final String scheme = uri.getScheme().toLowerCase(Locale.ROOT);
            final String host = uri.getHost().toLowerCase(Locale.ROOT);
            if (uri.getPort() >= 0) {
                return scheme + "://" + host + ":" + uri.getPort();
            }
            return scheme + "://" + host;
        } catch (final IllegalArgumentException ex) {
            return null;
        }
    }

    private String getPathWithinApplication(final HttpServletRequest request) {
        final String requestUri = request.getRequestURI();
        final String contextPath = request.getContextPath();
        if (StringUtils.hasText(contextPath) && requestUri.startsWith(contextPath)) {
            return requestUri.substring(contextPath.length());
        }
        return requestUri;
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
