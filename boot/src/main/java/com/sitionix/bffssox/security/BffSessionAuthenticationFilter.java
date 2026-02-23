package com.sitionix.bffssox.security;

import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSessionContextHolder;
import com.sitionix.bffssox.domain.BffSessionManager;
import com.sitionix.bffssox.domain.BffSessionProperties;
import com.sitionix.bffssox.domain.SessionCookieManager;
import com.sitionix.forge.security.userjwt.core.ForgeUser;
import com.sitionix.forge.security.userjwt.web.UserJwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class BffSessionAuthenticationFilter extends OncePerRequestFilter {

    private final BffSessionManager bffSessionManager;

    private final BffSessionProperties bffSessionProperties;

    private final SessionCookieManager sessionCookieManager;

    public BffSessionAuthenticationFilter(final BffSessionManager bffSessionManager,
                                          final BffSessionProperties bffSessionProperties,
                                          final SessionCookieManager sessionCookieManager) {
        this.bffSessionManager = bffSessionManager;
        this.bffSessionProperties = bffSessionProperties;
        this.sessionCookieManager = sessionCookieManager;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String path = this.resolvePath(request);
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String sessionId = this.readCookieValue(request, this.sessionCookieManager.sessionCookieName()).orElse(null);
        final Optional<BffResolvedSession> resolvedSession = sessionId == null
                ? Optional.empty()
                : this.bffSessionManager.resolveSession(
                        sessionId,
                        this.readUserAgent(request),
                        request.getRemoteAddr()
                );
        if (resolvedSession.isPresent()) {
            final BffResolvedSession session = resolvedSession.get();
            if (session.getPreviousSessionId() != null) {
                response.addHeader(HttpHeaders.SET_COOKIE, this.sessionCookieManager.createSessionCookie(session.getSessionId()));
            }
            response.addHeader(HttpHeaders.SET_COOKIE, this.sessionCookieManager.createCsrfCookie(session.getSession().getCsrfToken()));
            BffSessionContextHolder.set(session);
            SecurityContextHolder.getContext().setAuthentication(UserJwtAuthenticationToken.authenticated(
                    new ForgeUser(
                            session.getSession().getUserId(),
                            session.getSession().getEmail(),
                            List.of()
                    )
            ));
        }
        if (this.requiresAuthentication(path, request) && resolvedSession.isEmpty()) {
            this.writeUnauthorized(response);
            return;
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            BffSessionContextHolder.clear();
            SecurityContextHolder.clearContext();
        }
    }

    private boolean requiresAuthentication(final String path, final HttpServletRequest request) {
        final String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return false;
        }
        if (this.hasBearerAuthorization(request)) {
            return false;
        }
        return this.bffSessionProperties.getPublicPaths().stream()
                .noneMatch(publicPath -> this.matchesPath(path, publicPath));
    }

    private boolean hasBearerAuthorization(final HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorization != null && !authorization.isBlank();
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

    private String readUserAgent(final HttpServletRequest request) {
        final String value = request.getHeader(HttpHeaders.USER_AGENT);
        return value == null ? "" : value;
    }

    private void writeUnauthorized(final HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.addHeader(HttpHeaders.SET_COOKIE, this.sessionCookieManager.clearSessionCookie());
        response.addHeader(HttpHeaders.SET_COOKIE, this.sessionCookieManager.clearCsrfCookie());
        response.getWriter().write("""
                {"code":401,"title":"unauthorized","details":"Authentication required"}
                """);
    }
}
