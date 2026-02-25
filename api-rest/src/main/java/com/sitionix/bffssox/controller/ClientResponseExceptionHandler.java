package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.ErrorDTO;
import com.sitionix.bffssox.domain.ClientResponseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class ClientResponseExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleBadRequest(final IllegalArgumentException ex) {
        return this.asErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(final ConstraintViolationException ex) {
        final String details = ex.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse(ex.getMessage());
        return this.asErrorResponse(HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler({HandlerMethodValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDTO> handleValidationException(final Exception ex) {
        if (ex instanceof HandlerMethodValidationException handlerMethodValidationException) {
            final Optional<String> details = handlerMethodValidationException.getAllValidationResults().stream()
                    .flatMap(result -> result.getResolvableErrors().stream())
                    .map(error -> error.getDefaultMessage())
                    .findFirst();
            return this.asErrorResponse(HttpStatus.BAD_REQUEST, details.orElse("Validation failed"));
        }
        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            final String details = methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst()
                    .orElse("Validation failed");
            return this.asErrorResponse(HttpStatus.BAD_REQUEST, details);
        }
        return this.asErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed");
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ErrorDTO> handleMissingRequestCookieException(final MissingRequestCookieException ex) {
        return this.asErrorResponse(HttpStatus.UNAUTHORIZED, "Missing refresh cookie");
    }

    @ExceptionHandler(ClientResponseException.class)
    public ResponseEntity<String> handle(final ClientResponseException ex) {

        final int upstreamStatus = ex.getStatusCode();
        final int statusToClient = upstreamStatus >= 500
                ? HttpStatus.BAD_GATEWAY.value()
                : upstreamStatus;

        log.warn("Forwarding upstream error: upstreamStatus={}, statusToClient={}, body={}",
                upstreamStatus, statusToClient, ex.getResponseBody());

        return ResponseEntity.status(statusToClient)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ex.getResponseBody());
    }

    private ResponseEntity<ErrorDTO> asErrorResponse(final HttpStatus status, final String message) {
        return ResponseEntity.status(status)
                .body(ErrorDTO.builder()
                        .code(status.value())
                        .title(status.getReasonPhrase())
                        .details(message)
                        .build());
    }
}
