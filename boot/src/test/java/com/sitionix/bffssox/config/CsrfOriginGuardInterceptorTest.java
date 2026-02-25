package com.sitionix.bffssox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsrfOriginGuardInterceptorTest {

    @Mock
    private CorsProps corsProps;

    @Test
    void givenAllowedOrigin_whenPreHandle_thenReturnTrue() throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(
                List.of("http://localhost:3000", "http://localhost:3001")
        );
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        request.addHeader("Origin", "http://localhost:3000");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());

        //then
        assertThat(actual).isEqualTo(true);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void givenMissingOriginAndAllowedReferer_whenPreHandle_thenReturnTrue() throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(
                List.of("http://localhost:3000")
        );
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        request.addHeader("Referer", "http://localhost:3000/auth");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());

        //then
        assertThat(actual).isEqualTo(true);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void givenOriginWithUppercaseHost_whenPreHandle_thenReturnTrue() throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(
                List.of("https://localhost:3000")
        );
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        request.addHeader("Origin", "https://LOCALHOST:3000");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());

        //then
        assertThat(actual).isEqualTo(true);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void givenRefererWithUppercaseHost_whenPreHandle_thenReturnTrue() throws Exception {
        //given
        final CsrfOriginGuardInterceptor interceptor = this.csrfOriginGuardInterceptor(
                List.of("https://localhost:3000")
        );
        final MockHttpServletRequest request = this.request("POST", "/api/v1/auth/refresh");
        request.addHeader("Referer", "https://LOCALHOST:3000/auth?foo=bar");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final boolean actual = interceptor.preHandle(request, response, new Object());

        //then
        assertThat(actual).isEqualTo(true);
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
        assertThat(actual).isEqualTo(false);
        assertThat(response.getStatus()).isEqualTo(403);
        assertThat(payload.get("code")).isEqualTo("CSRF_ORIGIN");
        assertThat(payload.get("title")).isEqualTo("Forbidden");
        assertThat(payload.get("details")).isEqualTo("Invalid request origin");
        assertThat(payload.get("traceId")).isNotBlank();
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
}
