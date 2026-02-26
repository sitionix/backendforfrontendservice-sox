package com.sitionix.bffssox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.IDN;
import java.net.URI;
import java.net.URISyntaxException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CsrfOriginGuardInterceptor implements HandlerInterceptor {

    private static final String HTTP_SCHEME = "http";
    private static final String HTTPS_SCHEME = "https";
    private static final String B3_TRACE_ID_HEADER = "X-B3-TraceId";
    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String TRACE_ID_ATTRIBUTE = "traceId";
    private static final String FORBIDDEN_CODE = "CSRF_ORIGIN";
    private static final String FORBIDDEN_TITLE = "Forbidden";
    private static final String FORBIDDEN_DETAILS = "Invalid request origin";

    private static final Set<String> UNSAFE_METHODS = Set.of(
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name()
    );

    private final ObjectMapper objectMapper;
    private final Set<OriginKey> allowedOrigins;

    public CsrfOriginGuardInterceptor(final CorsProps corsProps,
                                      final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.allowedOrigins = this.resolveAllowedOrigins(corsProps.allowedOrigins());
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (!this.shouldEnforce(request)) {
            return true;
        }
        if (this.isAllowed(request)) {
            return true;
        }
        try {
            this.writeForbidden(request, response);
            return false;
        } catch (final IOException ex) {
            throw new IllegalStateException("Failed to write CSRF origin error response", ex);
        }
    }

    private boolean shouldEnforce(final HttpServletRequest request) {
        return UNSAFE_METHODS.contains(request.getMethod());
    }

    private boolean isAllowed(final HttpServletRequest request) {
        return this.resolveRequestOrigin(request)
                .map(this.allowedOrigins::contains)
                .orElse(false);
    }

    private Optional<OriginKey> resolveRequestOrigin(final HttpServletRequest request) {
        final String originHeader = request.getHeader(HttpHeaders.ORIGIN);
        if (StringUtils.hasText(originHeader)) {
            return this.parseOrigin(originHeader);
        }

        final String refererHeader = request.getHeader(HttpHeaders.REFERER);
        if (StringUtils.hasText(refererHeader)) {
            return this.parseOrigin(refererHeader);
        }

        return Optional.empty();
    }

    private Set<OriginKey> resolveAllowedOrigins(final List<String> configuredOrigins) {
        if (Objects.isNull(configuredOrigins) || configuredOrigins.isEmpty()) {
            return Set.of();
        }

        return configuredOrigins.stream()
                .map(this::parseOrigin)
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Optional<OriginKey> parseOrigin(final String value) {
        if (!StringUtils.hasText(value)) {
            return Optional.empty();
        }

        try {
            final URI uri = new URI(value.trim());
            final String scheme = this.normalizeScheme(uri.getScheme());
            final String host = this.normalizeHost(uri.getHost());
            if (Objects.isNull(scheme) || Objects.isNull(host) || StringUtils.hasText(uri.getRawUserInfo())) {
                return Optional.empty();
            }

            return Optional.of(new OriginKey(scheme, host, this.resolvePort(scheme, uri.getPort())));
        } catch (final URISyntaxException | IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    private String normalizeScheme(final String scheme) {
        if (!StringUtils.hasText(scheme)) {
            return null;
        }

        final String normalizedScheme = scheme.toLowerCase(Locale.ROOT);
        if (HTTP_SCHEME.equals(normalizedScheme) || HTTPS_SCHEME.equals(normalizedScheme)) {
            return normalizedScheme;
        }
        return null;
    }

    private String normalizeHost(final String host) {
        if (!StringUtils.hasText(host)) {
            return null;
        }

        final String asciiHost = IDN.toASCII(host, IDN.ALLOW_UNASSIGNED);
        if (!StringUtils.hasText(asciiHost)) {
            return null;
        }
        return asciiHost.toLowerCase(Locale.ROOT);
    }

    private int resolvePort(final String scheme, final int port) {
        if (port > 0) {
            return port;
        }
        return HTTPS_SCHEME.equals(scheme) ? 443 : 80;
    }

    private void writeForbidden(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final Map<String, String> body = new LinkedHashMap<>();
        body.put("code", FORBIDDEN_CODE);
        body.put("title", FORBIDDEN_TITLE);
        body.put("details", FORBIDDEN_DETAILS);
        body.put(TRACE_ID_ATTRIBUTE, this.resolveTraceId(request));

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(this.objectMapper.writeValueAsString(body));
    }

    private String resolveTraceId(final HttpServletRequest request) {
        final String b3TraceId = request.getHeader(B3_TRACE_ID_HEADER);
        if (StringUtils.hasText(b3TraceId)) {
            return b3TraceId;
        }

        final String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (StringUtils.hasText(requestId)) {
            return requestId;
        }

        final Object traceId = request.getAttribute(TRACE_ID_ATTRIBUTE);
        if (traceId instanceof String value && StringUtils.hasText(value)) {
            return value;
        }

        return UUID.randomUUID().toString();
    }

    private record OriginKey(String scheme, String host, int port) {
    }
}
