package com.sitionix.bffssox.controller;

import com.sitionix.bffssox.domain.ClientResponseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientResponseExceptionHandler {

    @ExceptionHandler(ClientResponseException.class)
    public ResponseEntity<String> handleClientResponseException(final ClientResponseException ex) {
        final HttpHeaders headers = new HttpHeaders();
        for (Map.Entry<String, List<String>> entry : ex.getResponseHeaders().entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            headers.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        final int statusCode = ex.getStatusCode() >= 500 ? 502 : ex.getStatusCode();
        return ResponseEntity.status(statusCode)
                .headers(headers)
                .body(ex.getResponseBody());
    }
}
