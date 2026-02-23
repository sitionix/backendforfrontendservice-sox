package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.BffSessionContextHolder;
import com.sitionix.bffssox.domain.BffSessionManager;
import com.sitionix.bffssox.domain.ClientResponseException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)

class WagssoxClientCallExecutorTest {

    private WagssoxClientCallExecutor wagssoxClientCallExecutor;

    @Mock
    private BffSessionManager bffSessionManager;

    @BeforeEach
    void setUp() {
        this.wagssoxClientCallExecutor = new WagssoxClientCallExecutor(this.bffSessionManager);
    }

    @AfterEach
    void tearDown() {
        BffSessionContextHolder.clear();
        verifyNoMoreInteractions(this.bffSessionManager);
    }

    @Test
    void givenSuccessSupplier_whenExecute_thenReturnValue() {
        //given

        //when
        final String result = this.wagssoxClientCallExecutor.execute(() -> "ok");

        //then
        assertThat(result).isEqualTo("ok");
    }

    @Test
    void givenHttpError_whenExecute_thenThrowClientResponseExceptionWithBody() {
        //given
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Trace-Id", "trace-123");
        final byte[] body = "{\"error\":\"bad request\"}".getBytes(StandardCharsets.UTF_8);
        final HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                headers,
                body,
                StandardCharsets.UTF_8
        );

        //when then
        assertThatThrownBy(() -> this.wagssoxClientCallExecutor.execute(() -> {
            throw exception;
        }))
                .isInstanceOf(ClientResponseException.class)
                .satisfies(thrown -> {
                    final ClientResponseException responseException = (ClientResponseException) thrown;
                    assertThat(responseException.getStatusCode()).isEqualTo(400);
                    assertThat(responseException.getResponseBody()).isEqualTo("{\"error\":\"bad request\"}");
                    assertThat(responseException.getResponseHeaders())
                            .containsKey(HttpHeaders.CONTENT_TYPE)
                            .containsKey("X-Trace-Id");
                });
    }
}
