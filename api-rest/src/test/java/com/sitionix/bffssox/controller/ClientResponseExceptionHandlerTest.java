package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.ErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        this.clientResponseExceptionHandler = new ClientResponseExceptionHandler(new ObjectMapper());
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
    void givenResourceAccessException_whenHandleResourceAccessException_thenReturnsServiceUnavailable() {
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
    void givenClientResponseExceptionWithUnauthorizedAndJsonBody_whenHandle_thenReturnsUnauthorizedWithUpstreamBody() {
        //given
        final ClientResponseException exception = new ClientResponseException(
                HttpStatus.UNAUTHORIZED.value(),
                "{\"code\":401,\"title\":\"unauthorized\",\"details\":\"Invalid credentials\"}",
                Collections.emptyMap(),
                new RuntimeException("Unauthorized")
        );

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handle(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .title("unauthorized")
                        .details("Invalid credentials")
                        .build()
        );
    }

    @Test
    void givenClientResponseExceptionWithServerErrorAndJsonBody_whenHandle_thenReturnsServerErrorWithUpstreamBody() {
        //given
        final ClientResponseException exception = new ClientResponseException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "{\"code\":502,\"title\":\"upstream_error\",\"details\":\"AuthSox service failed\"}",
                Collections.emptyMap(),
                new RuntimeException("Upstream failed")
        );

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handle(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(502)
                        .title("upstream_error")
                        .details("AuthSox service failed")
                        .build()
        );
    }

    @Test
    void givenClientResponseExceptionWithInvalidJsonBody_whenHandle_thenReturnsFallbackError() {
        //given
        final ClientResponseException exception = new ClientResponseException(
                HttpStatus.UNAUTHORIZED.value(),
                "not-json",
                Collections.emptyMap(),
                new RuntimeException("Unauthorized")
        );

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handle(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .title(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .details(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .build()
        );
    }

    @Test
    void givenClientResponseExceptionWithUnknownStatusCode_whenHandle_thenReturnsBadGatewayError() {
        //given
        final ClientResponseException exception = new ClientResponseException(
                599,
                "{\"code\":599,\"title\":\"unknown\",\"details\":\"Unknown status\"}",
                Collections.emptyMap(),
                new RuntimeException("Unknown")
        );

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handle(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_GATEWAY);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_GATEWAY.value())
                        .title(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                        .details("Invalid upstream response status")
                        .build()
        );
    }

}
