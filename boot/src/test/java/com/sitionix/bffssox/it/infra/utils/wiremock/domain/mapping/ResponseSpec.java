package com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSpec {
    private int status;
    private Map<String, String> headers;
    private String body;
    private String bodyFileName;
}

