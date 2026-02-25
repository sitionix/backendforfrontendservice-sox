package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.ErrorDTO;
import com.sitionix.bffssox.domain.ClientResponseException;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.client.ResourceAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ClientResponseExceptionHandlerTest {

    private ClientResponseExceptionHandler clientResponseExceptionHandler;

    @BeforeEach
    void setUp() {
        this.clientResponseExceptionHandler = new ClientResponseExceptionHandler();
    }

    @Test
    void givenMissingRefreshCookie_whenHandleMissingRequestCookieException_thenReturnsUnauthorizedErrorResponse() {
        //given
        final MissingRequestCookieException exception = mock(MissingRequestCookieException.class);

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler
                .handleMissingRequestCookieException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .title(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .details("Missing refresh cookie")
                        .build()
        );
    }

    @Test
    void givenResourceAccessExceptionWithoutTimeoutCause_whenHandleResourceAccessException_thenReturnsServiceUnavailable() {
        //given
        final ResourceAccessException exception = new ResourceAccessException("Connection refused");

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handleResourceAccessException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .title(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                        .details("Upstream service unavailable")
                        .build()
        );
    }

    @Test
    void givenResourceAccessExceptionWithTimeoutCause_whenHandleResourceAccessException_thenReturnsServiceUnavailable() {
        //given
        final ResourceAccessException exception = new ResourceAccessException("Read timed out");

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handleResourceAccessException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .title(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                        .details("Upstream service unavailable")
                        .build()
        );
    }

    @Test
    void givenClientResponseExceptionWithServerError_whenHandle_thenReturnsServiceUnavailable() {
        //given
        final ClientResponseException exception = new ClientResponseException(
                HttpStatus.BAD_GATEWAY.value(),
                "{\"code\":502,\"title\":\"upstream_error\",\"details\":\"AuthSox service failed\"}",
                Collections.emptyMap(),
                new RuntimeException("Upstream failed")
        );

        //when
        final ResponseEntity<?> actual = this.clientResponseExceptionHandler.handle(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .title(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                        .details("Upstream service unavailable")
                        .build()
        );
    }

    @Test
    void givenClientResponseExceptionWithClientError_whenHandle_thenReturnsServiceUnavailable() {
        //given
        final ClientResponseException exception = new ClientResponseException(
                HttpStatus.UNAUTHORIZED.value(),
                "{\"code\":401,\"title\":\"Unauthorized\",\"details\":\"Bad token\"}",
                Collections.emptyMap(),
                new RuntimeException("Unauthorized")
        );

        //when
        final ResponseEntity<?> actual = this.clientResponseExceptionHandler.handle(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .title(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                        .details("Upstream service unavailable")
                        .build()
        );
    }
}
