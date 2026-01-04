package com.sitionix.bffssox.controller;

import com.sitionix.bffssox.domain.ClientResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ClientResponseExceptionHandler {

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
}
