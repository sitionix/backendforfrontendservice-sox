package com.sitionix.bffssox.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshCookieManagerTest {

    @Test
    void givenRefreshToken_whenBuildRefreshCookies_thenReturnsSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final RefreshCookieManager manager = new RefreshCookieManager(props);

        //when
        final List<String> cookies = manager.buildRefreshCookies("refresh-token");

        //then
        assertThat(cookies).hasSize(1);
        final String setCookie = cookies.get(0);
        assertThat(setCookie).isNotBlank();
        assertThat(setCookie).contains("__Host-refresh_token=refresh-token");
        assertThat(setCookie).contains("Path=/");
        assertThat(setCookie).contains("Secure");
        assertThat(setCookie).contains("HttpOnly");
        assertThat(setCookie).contains("SameSite=Lax");
    }

    @Test
    void givenBlankToken_whenBuildRefreshCookies_thenReturnsEmptyList() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final RefreshCookieManager manager = new RefreshCookieManager(props);

        //when
        final List<String> cookies = manager.buildRefreshCookies(" ");

        //then
        assertThat(cookies).isEmpty();
    }

    @Test
    void givenCookies_whenBuildSetCookieHeaders_thenReturnsHeadersWithSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final RefreshCookieManager manager = new RefreshCookieManager(props);
        final List<String> cookies = List.of("cookie-value");

        //when
        final HttpHeaders headers = manager.buildSetCookieHeaders(cookies);

        //then
        assertThat(headers.get(HttpHeaders.SET_COOKIE)).containsExactly("cookie-value");
    }

    @Test
    void givenRefreshToken_whenBuildRefreshCookieHeaders_thenReturnsHeadersWithSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final RefreshCookieManager manager = new RefreshCookieManager(props);

        //when
        final HttpHeaders headers = manager.buildRefreshCookieHeaders("refresh-token");

        //then
        assertThat(headers.get(HttpHeaders.SET_COOKIE))
                .singleElement()
                .contains("__Host-refresh_token=refresh-token");
    }

    @Test
    void givenBlankToken_whenBuildRefreshCookieHeaders_thenReturnsHeadersWithoutSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final RefreshCookieManager manager = new RefreshCookieManager(props);

        //when
        final HttpHeaders headers = manager.buildRefreshCookieHeaders(" ");

        //then
        assertThat(headers.containsKey(HttpHeaders.SET_COOKIE)).isFalse();
    }
}
