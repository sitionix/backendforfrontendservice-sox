package com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestSpec {
    private String url;
    private String urlPath;
    private String method;
    private Map<String, QueryParamPattern> queryParameters;
    private List<BodyPattern> bodyPatterns;
    private Map<String, HeaderPattern> headers;
}