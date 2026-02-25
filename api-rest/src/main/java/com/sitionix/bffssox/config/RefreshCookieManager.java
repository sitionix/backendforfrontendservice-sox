package com.sitionix.bffssox.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RefreshCookieManager {

    private final RefreshCookieProps props;

    public List<String> buildRefreshCookies(final String refreshToken) {
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
}
