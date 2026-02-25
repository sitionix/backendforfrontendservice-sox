package com.sitionix.bffssox.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshCookieManagerTest {

    @Test
    void givenRefreshToken_whenAddRefreshCookieHeader_thenAddsSetCookie() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final RefreshCookieManager manager = new RefreshCookieManager(props);
        final HttpHeaders headers = new HttpHeaders();

        //when
        manager.addRefreshCookieHeader(headers, "refresh-token");

        //then
        final String setCookie = headers.getFirst(HttpHeaders.SET_COOKIE);
        assertThat(setCookie).isNotBlank();
        assertThat(setCookie).contains("__Host-refresh_token=refresh-token");
        assertThat(setCookie).contains("Path=/");
        assertThat(setCookie).contains("Secure");
        assertThat(setCookie).contains("HttpOnly");
        assertThat(setCookie).contains("SameSite=Lax");
    }

    @Test
    void givenBlankToken_whenAddRefreshCookieHeader_thenDoesNotAddHeader() {
        //given
        final RefreshCookieProps props = new RefreshCookieProps("__Host-refresh_token", true, "Lax", "/", true);
        final RefreshCookieManager manager = new RefreshCookieManager(props);
        final HttpHeaders headers = new HttpHeaders();

        //when
        manager.addRefreshCookieHeader(headers, " ");

        //then
        assertThat(headers.containsKey(HttpHeaders.SET_COOKIE)).isFalse();
    }
}
