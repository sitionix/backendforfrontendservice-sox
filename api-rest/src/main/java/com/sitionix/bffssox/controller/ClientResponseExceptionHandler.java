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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class ClientResponseExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleBadRequest(final IllegalArgumentException ex) {
        return this.asBadRequest(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(final ConstraintViolationException ex) {
        final String details = ex.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse(ex.getMessage());
        return this.asBadRequest(details);
    }

    @ExceptionHandler({HandlerMethodValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDTO> handleValidationException(final Exception ex) {
        if (ex instanceof HandlerMethodValidationException handlerMethodValidationException) {
            final Optional<String> details = handlerMethodValidationException.getAllValidationResults().stream()
                    .flatMap(result -> result.getResolvableErrors().stream())
                    .map(error -> error.getDefaultMessage())
                    .findFirst();
            return this.asBadRequest(details.orElse("Validation failed"));
        }
        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            final String details = methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst()
                    .orElse("Validation failed");
            return this.asBadRequest(details);
        }
        return this.asBadRequest("Validation failed");
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

    private ResponseEntity<ErrorDTO> asBadRequest(final String details) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .details(details)
                        .build());
    }
}
