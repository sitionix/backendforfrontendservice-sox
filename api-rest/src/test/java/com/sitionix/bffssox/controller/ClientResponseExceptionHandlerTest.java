package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.ErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitionix.bffssox.domain.ClientResponseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.client.ResourceAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void givenInvalidSiteId_whenHandleMethodArgumentTypeMismatchException_thenReturnsBadRequest() {
        //given
        final MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "not-a-valid-id",
                UUID.class,
                "siteId",
                null,
                new IllegalArgumentException("Invalid siteId")
        );

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handleMethodArgumentTypeMismatchException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .details("Invalid siteId")
                        .build()
        );
    }

    @Test
    void givenInvalidAgentId_whenHandleMethodArgumentTypeMismatchException_thenReturnsBadRequest() {
        //given
        final MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "not-a-valid-id",
                UUID.class,
                "agentId",
                null,
                new IllegalArgumentException("Invalid agentId")
        );

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handleMethodArgumentTypeMismatchException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .details("Invalid agentId")
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

    @Test
    void givenIllegalArgumentException_whenHandleBadRequest_thenReturnsBadRequestError() {
        //given
        final IllegalArgumentException exception = new IllegalArgumentException("invalid payload");

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handleBadRequest(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .details("invalid payload")
                        .build()
        );
    }

    @Test
    void givenConstraintViolationException_whenHandleConstraintViolationException_thenReturnsFirstViolationMessage() {
        //given
        final ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("must not be blank");
        final ConstraintViolationException exception = new ConstraintViolationException("invalid", Set.of(violation));

        //when
        final ResponseEntity<ErrorDTO> actual =
                this.clientResponseExceptionHandler.handleConstraintViolationException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .details("must not be blank")
                        .build()
        );
    }

    @Test
    void givenMethodArgumentNotValidException_whenHandleValidationException_thenReturnsBindingMessage() {
        //given
        final BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.reject("invalid", "payload validation failed");
        final MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handleValidationException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .details("payload validation failed")
                        .build()
        );
    }

    @Test
    void givenUnknownValidationException_whenHandleValidationException_thenReturnsFallbackMessage() {
        //given
        final Exception exception = new RuntimeException("unknown");

        //when
        final ResponseEntity<ErrorDTO> actual = this.clientResponseExceptionHandler.handleValidationException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody()).isEqualTo(
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .details("Validation failed")
                        .build()
        );
    }

    @Test
    void givenInvalidUnknownParameter_whenHandleMethodArgumentTypeMismatchException_thenReturnsBadRequestWithExceptionMessage() {
        //given
        final MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "abc",
                UUID.class,
                "otherParam",
                null,
                new IllegalArgumentException("Type mismatch details")
        );

        //when
        final ResponseEntity<ErrorDTO> actual =
                this.clientResponseExceptionHandler.handleMethodArgumentTypeMismatchException(exception);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actual.getBody().getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(actual.getBody().getDetails()).contains("Type mismatch details");
    }

}
