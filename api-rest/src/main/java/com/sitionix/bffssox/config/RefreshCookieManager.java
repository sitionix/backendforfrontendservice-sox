package com.sitionix.bffssox.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class RefreshCookieManager {

    private final RefreshCookieProps props;

    public void addRefreshCookieHeader(final HttpHeaders headers, final String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            return;
        }

        final String cookieValue = ResponseCookie.from(this.props.name(), refreshToken)
                .httpOnly(this.props.httpOnly())
                .secure(this.props.secure())
                .sameSite(this.props.sameSite())
                .path(this.props.path())
                .build()
                .toString();

        headers.add(HttpHeaders.SET_COOKIE, cookieValue);
    }
}
