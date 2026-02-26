package com.sitionix.bffssox.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CookieHeaderFactory {

    private final RefreshCookieProps props;

    private List<String> buildRefreshCookies(final String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            return List.of();
        }

        final String cookieValue = ResponseCookie.from(this.props.name(), refreshToken)
                .httpOnly(this.props.httpOnly())
                .secure(this.props.secure())
                .sameSite(this.props.sameSite())
                .path(this.props.path())
                .build()
                .toString();

        return List.of(cookieValue);
    }

    public HttpHeaders buildSetCookieHeaders(final List<String> cookies) {
        final HttpHeaders headers = new HttpHeaders();
        if (Objects.isNull(cookies) || cookies.isEmpty()) {
            return headers;
        }
        headers.addAll(HttpHeaders.SET_COOKIE, cookies);
        return headers;
    }

    public HttpHeaders buildRefreshCookieHeaders(final String refreshToken) {
        return this.buildSetCookieHeaders(this.buildRefreshCookies(refreshToken));
    }
}
