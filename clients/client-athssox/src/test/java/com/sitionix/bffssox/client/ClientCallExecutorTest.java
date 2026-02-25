package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.ClientResponseException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClientCallExecutorTest {

    private ClientCallExecutor clientCallExecutor;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.clientCallExecutor = new ClientCallExecutor();
    }

    @Test
    void givenSuccessSupplier_whenExecute_thenReturnValue() {
        final String result = this.clientCallExecutor.execute(() -> "ok");

        assertThat(result).isEqualTo("ok");
    }

    @Test
    void givenHttpError_whenExecute_thenThrowClientResponseExceptionWithBody() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Trace-Id", "trace-123");
        final byte[] body = "{\"error\":\"unauthorized\"}".getBytes(StandardCharsets.UTF_8);
        final HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                headers,
                body,
                StandardCharsets.UTF_8
        );

        assertThatThrownBy(() -> this.clientCallExecutor.execute(() -> {
            throw exception;
        }))
                .isInstanceOf(ClientResponseException.class)
                .satisfies(thrown -> {
                    final ClientResponseException responseException = (ClientResponseException) thrown;
                    assertThat(responseException.getStatusCode()).isEqualTo(401);
                    assertThat(responseException.getResponseBody()).isEqualTo("{\"error\":\"unauthorized\"}");
                    assertThat(responseException.getResponseHeaders())
                            .containsKey(HttpHeaders.CONTENT_TYPE)
                            .containsKey("X-Trace-Id");
                });
    }

    @Test
    void givenResourceAccessException_whenExecute_thenThrowClientResponseExceptionWithBadGateway() {
        final ResourceAccessException exception = new ResourceAccessException("Connection refused");

        assertThatThrownBy(() -> this.clientCallExecutor.execute(() -> {
            throw exception;
        }))
                .isInstanceOf(ClientResponseException.class)
                .satisfies(thrown -> {
                    final ClientResponseException responseException = (ClientResponseException) thrown;
                    assertThat(responseException.getStatusCode()).isEqualTo(502);
                    assertThat(responseException.getResponseBody()).isEqualTo(
                            "{\"code\":502,\"title\":\"upstream_error\",\"details\":\"Upstream service unavailable\"}");
                });
    }
}
