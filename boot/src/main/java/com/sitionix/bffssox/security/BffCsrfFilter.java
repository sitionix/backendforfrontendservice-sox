package com.sitionix.bffssox.security;

import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSessionContextHolder;
import com.sitionix.bffssox.domain.BffSessionProperties;
import com.sitionix.bffssox.domain.SessionCookieManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class BffCsrfFilter extends OncePerRequestFilter {

    private final BffSessionProperties bffSessionProperties;

    private final SessionCookieManager sessionCookieManager;

    public BffCsrfFilter(final BffSessionProperties bffSessionProperties,
                         final SessionCookieManager sessionCookieManager) {
        this.bffSessionProperties = bffSessionProperties;
        this.sessionCookieManager = sessionCookieManager;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        if (!this.bffSessionProperties.getCsrf().isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }
        final BffResolvedSession resolvedSession = BffSessionContextHolder.get();
        if (resolvedSession == null) {
            filterChain.doFilter(request, response);
            return;
        }
        final String path = this.resolvePath(request);
        if (!this.requiresCsrf(path, request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        final String expectedToken = resolvedSession.getSession().getCsrfToken();
        final String headerToken = request.getHeader(this.bffSessionProperties.getCsrf().getHeaderName());
        final String cookieToken = this.readCookieValue(request, this.sessionCookieManager.csrfCookieName()).orElse(null);
        if (expectedToken == null || !expectedToken.equals(headerToken) || !expectedToken.equals(cookieToken)) {
            this.writeForbidden(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean requiresCsrf(final String path, final String method) {
        final boolean protectedMethod = this.bffSessionProperties.getCsrf().getProtectedMethods().stream()
                .anyMatch(value -> value.equalsIgnoreCase(method));
        if (!protectedMethod) {
            return false;
        }
        return this.bffSessionProperties.getCsrf().getIgnorePaths().stream()
                .noneMatch(configuredPath -> this.matchesPath(path, configuredPath));
    }

    private boolean matchesPath(final String path, final String configuredPath) {
        if (configuredPath.endsWith("/**")) {
            return path.startsWith(configuredPath.substring(0, configuredPath.length() - 3));
        }
        return path.equals(configuredPath);
    }

    private String resolvePath(final HttpServletRequest request) {
        final String contextPath = request.getContextPath();
        final String requestUri = request.getRequestURI();
        if (contextPath == null || contextPath.isBlank()) {
            return requestUri;
        }
        if (!requestUri.startsWith(contextPath)) {
            return requestUri;
        }
        final String result = requestUri.substring(contextPath.length());
        return result.isBlank() ? "/" : result;
    }

    private Optional<String> readCookieValue(final HttpServletRequest request, final String cookieName) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .filter(value -> value != null && !value.isBlank())
                .findFirst();
    }

    private void writeForbidden(final HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("""
                {"code":403,"title":"Forbidden","details":"Invalid CSRF token"}
                """);
    }
}
