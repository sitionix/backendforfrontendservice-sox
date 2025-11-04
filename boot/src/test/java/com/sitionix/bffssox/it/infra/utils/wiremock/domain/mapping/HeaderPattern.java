package com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HeaderPattern {
    private String equalTo;
    private String matches;
    private String contains;

    public static HeaderPattern equalTo(final MediaType value) {
        return new HeaderPattern(value.toString(), null, null);
    }

    public static HeaderPattern matches(final String value) {
        return new HeaderPattern(null, value, null);
    }

    public static HeaderPattern contains(final String value) {
        return new HeaderPattern(null, null, value);
    }
}

