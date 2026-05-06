package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.ErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitionix.bffssox.domain.ClientResponseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ClientResponseExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleBadRequest(final IllegalArgumentException ex) {
        return this.asErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        if ("siteId".equals(ex.getName())) {
            return this.asErrorResponse(HttpStatus.BAD_REQUEST, "Invalid siteId");
        }
        if ("agentId".equals(ex.getName())) {
            return this.asErrorResponse(HttpStatus.BAD_REQUEST, "Invalid agentId");
        }
        if ("projectId".equals(ex.getName())) {
            return this.asErrorResponse(HttpStatus.BAD_REQUEST, "Invalid projectId");
        }
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

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorDTO> handleResourceAccessException(final ResourceAccessException ex) {
        final HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        final String details = "Upstream service unavailable";
        log.warn(
                "Upstream transport error: statusToClient={}, causeType={}, causeMessage={}",
                status.value(),
                ex.getClass().getSimpleName(),
                ex.getMostSpecificCause().getMessage()
        );
        return this.asErrorResponse(status, details);
    }

    @ExceptionHandler(ClientResponseException.class)
    public ResponseEntity<ErrorDTO> handle(final ClientResponseException ex) {
        final HttpStatus status = HttpStatus.resolve(ex.getStatusCode());
        if (status == null) {
            log.warn("Unknown upstream status code: {}", ex.getStatusCode());
            return this.asErrorResponse(HttpStatus.BAD_GATEWAY, "Invalid upstream response status");
        }

        final ErrorDTO upstreamError = this.parseError(ex.getResponseBody());
        this.logUpstreamFailure(ex.getStatusCode(), upstreamError);
        if (upstreamError != null) {
            return ResponseEntity.status(status).body(upstreamError);
        }

        return this.asErrorResponse(status, status.getReasonPhrase());
    }

    private ErrorDTO parseError(final String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return null;
        }

        try {
            return this.objectMapper.readValue(responseBody, ErrorDTO.class);
        } catch (Exception ex) {
            log.warn("Failed to parse upstream error body", ex);
            return null;
        }
    }

    private ResponseEntity<ErrorDTO> asErrorResponse(final HttpStatus status, final String message) {
        return ResponseEntity.status(status)
                .body(ErrorDTO.builder()
                        .code(status.value())
                        .title(status.getReasonPhrase())
                        .details(message)
                        .build());
    }

    private void logUpstreamFailure(final int statusCode, final ErrorDTO upstreamError) {
        if (upstreamError == null || !StringUtils.hasText(upstreamError.getDetails())) {
            return;
        }
        if (!upstreamError.getDetails().toLowerCase().contains("internal authorization token")) {
            return;
        }
        log.warn(
                "Upstream internal auth failure: statusCode={}, title={}, details={}",
                statusCode,
                upstreamError.getTitle(),
                upstreamError.getDetails()
        );
    }

}
