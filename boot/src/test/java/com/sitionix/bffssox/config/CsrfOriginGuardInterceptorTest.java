package com.sitionix.bffssox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsrfOriginGuardInterceptorTest {

    @Mock
    private CorsProps corsProps;

    @ParameterizedTest
    @MethodSource("allowedOriginRequests")
    void givenAllowedOriginRequest_whenPreHandle_thenReturnTrue(final List<String> allowedOrigins,
                                                                final String headerName,
                                                                final String headerValue) throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(allowedOrigins);
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        request.addHeader(headerName, headerValue);
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());

        //then
        assertThat(actual).isTrue();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void givenMissingOriginAndReferer_whenPreHandle_thenReturnForbiddenPayload() throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(
                List.of("http://localhost:3000")
        );
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());
        final Map<String, String> payload = new ObjectMapper().readValue(
                response.getContentAsString(),
                new TypeReference<>() {
                }
        );

        //then
        assertThat(actual).isFalse();
        assertThat(response.getStatus()).isEqualTo(403);
        assertThat(payload)
                .containsEntry("code", "CSRF_ORIGIN")
                .containsEntry("title", "Forbidden")
                .containsEntry("details", "Invalid request origin");
        assertThat(payload.get("traceId")).isNotBlank();
    }

    @Test
    void givenAllowedRefererWithDefaultHttpsPort_whenPreHandle_thenReturnTrue() throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(
                List.of("https://localhost")
        );
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        request.addHeader("Referer", "https://localhost/auth");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());

        //then
        assertThat(actual).isTrue();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void givenInvalidOriginScheme_whenPreHandle_thenReturnForbiddenPayload() throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(
                List.of("https://localhost:3000")
        );
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        request.addHeader("Origin", "javascript://localhost:3000");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());

        //then
        assertThat(actual).isFalse();
        assertThat(response.getStatus()).isEqualTo(403);
    }

    private CsrfOriginGuardInterceptor csrfOriginGuardInterceptor(
            final List<String> allowedOrigins
    ) {
        when(this.corsProps.allowedOrigins()).thenReturn(allowedOrigins);
        return new CsrfOriginGuardInterceptor(this.corsProps, new ObjectMapper());
    }

    private MockHttpServletRequest request(final String method, final String path) {
        final MockHttpServletRequest request = new MockHttpServletRequest(method, "/bffssox" + path);
        request.setContextPath("/bffssox");
        return request;
    }

    private static Stream<Arguments> allowedOriginRequests() {
        return Stream.of(
                Arguments.of(
                        List.of("http://localhost:3000", "http://localhost:3001"),
                        "Origin",
                        "http://localhost:3000"
                ),
                Arguments.of(
                        List.of("http://localhost:3000"),
                        "Referer",
                        "http://localhost:3000/auth"
                ),
                Arguments.of(
                        List.of("https://localhost:3000"),
                        "Origin",
                        "https://LOCALHOST:3000"
                ),
                Arguments.of(
                        List.of("https://localhost:3000"),
                        "Referer",
                        "https://LOCALHOST:3000/auth?foo=bar"
                )
        );
    }
}
