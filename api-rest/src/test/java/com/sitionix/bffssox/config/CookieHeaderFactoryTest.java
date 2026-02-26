package com.sitionix.bffssox.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

class CookieHeaderFactoryTest {

    @Test
    void givenRefreshToken_whenBuildRefreshCookieHeaders_thenReturnsSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final CookieHeaderFactory manager = new CookieHeaderFactory(props);

        //when
        final HttpHeaders headers = manager.buildRefreshCookieHeaders("refresh-token");

        //then
        assertThat(headers.get(HttpHeaders.SET_COOKIE))
                .singleElement()
                .asString()
                .contains("__Host-refresh_token=refresh-token")
                .contains("Path=/")
                .contains("Secure")
                .contains("HttpOnly")
                .contains("SameSite=Lax");
    }

    @Test
    void givenBlankToken_whenBuildRefreshCookieHeaders_thenReturnsHeadersWithoutSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final CookieHeaderFactory manager = new CookieHeaderFactory(props);

        //when
        final HttpHeaders headers = manager.buildRefreshCookieHeaders(" ");

        //then
        assertThat(headers.containsKey(HttpHeaders.SET_COOKIE)).isFalse();
    }

    @Test
    void givenCookies_whenBuildSetCookieHeaders_thenReturnsHeadersWithSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final CookieHeaderFactory manager = new CookieHeaderFactory(props);
        final List<String> cookies = List.of("cookie-value");

        //when
        final HttpHeaders headers = manager.buildSetCookieHeaders(cookies);

        //then
        assertThat(headers.get(HttpHeaders.SET_COOKIE)).containsExactly("cookie-value");
    }

}
