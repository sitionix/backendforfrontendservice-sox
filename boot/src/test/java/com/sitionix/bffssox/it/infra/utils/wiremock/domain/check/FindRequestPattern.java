package com.sitionix.bffssox.it.infra.utils.wiremock.domain.check;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpMethod;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FindRequestPattern(String method, String urlPattern) {
    public static FindRequestPattern findPostByUrl(final String urlPattern) {
        return new FindRequestPattern(HttpMethod.POST.name(), urlPattern);
    }
}
