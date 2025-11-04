package com.sitionix.bffssox.it.infra.utils.wiremock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.FindRequestPattern;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping.StubMappingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WireMockJournalClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public UUID createMapping(final StubMappingRequest request) {
        final String response = this.restClient.post()
                .uri("/mappings")
                .body(request)
                .retrieve()
                .toEntity(String.class)
                .getBody();
        try {
            final JsonNode root = this.objectMapper.readTree(response);
            final JsonNode id = root.path("id");
            if (id.isMissingNode() || id.isNull()) {
                throw new RuntimeException("Invalid WireMock /mappings response: " + response);
            }
            return UUID.fromString(id.asText());
        } catch (final Exception e) {
            throw new RuntimeException("Cannot parse WireMock /mappings response: " + response, e);
        }
    }

    public void deleteMapping(final UUID id) {
        this.restClient.delete()
                .uri("/mappings/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public void reset() {
        this.restClient.post()
                .uri("/requests/reset")
                .retrieve()
                .toBodilessEntity();
    }

    public List<String> findBodiesByUrl(final String url) {
        final ResponseEntity<String> response = this.restClient.post()
                .uri("/requests/find")
                .body(FindRequestPattern.findPostByUrl(url))
                .retrieve()
                .toEntity(String.class);
        return this.extractBodies(response.getBody());
    }

    private List<String> extractBodies(final String response) {
        try {
            final JsonNode root = this.objectMapper.readTree(response);
            final JsonNode arr = root.path("requests");
            if (!arr.isArray() || arr.isEmpty()) {
                return Collections.emptyList();
            }
            final List<String> bodies = new ArrayList<>(arr.size());
            for (final JsonNode item : arr) {
                final JsonNode body = item.get("body");
                if (body != null && !body.isNull()) {
                    bodies.add(body.asText());
                }
            }
            return bodies;
        } catch (final Exception e) {
            throw new RuntimeException("Cannot parse WireMock /requests/find response", e);
        }
    }
}
