package com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryParamPattern {
    private String equalTo;
    private String matches;
    private String contains;
}
