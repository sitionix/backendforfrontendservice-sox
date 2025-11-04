package com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StubMappingRequest {
    private String name;
    private RequestSpec request;
    private ResponseSpec response;
    private boolean persistent;
    private String scenarioName;
    private String requiredScenarioState;
    private String newScenarioState;
}