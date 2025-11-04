package com.sitionix.bffssox.it.infra.utils.wiremock.domain.check;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import com.sitionix.bffssox.it.infra.utils.loader.ResourceLoader;
import lombok.Getter;

public class RequestBuilder {

    private final ResourceLoader resourceLoader;
    private final List<String> ignoreFields;
    private final Consumer<WireMockCheck> verifier;

    private String endpoint;
    private String jsonValue;
    private int atLeastTimes;
    private boolean cleanAfterVerify = false;

    @Getter
    private UUID id;

    public RequestBuilder(final ResourceLoader resourceLoader, final Consumer<WireMockCheck> verifier) {
        this.resourceLoader = resourceLoader;
        this.verifier = verifier;
        this.ignoreFields = new ArrayList<>();
        this.atLeastTimes = 1;
    }

    public RequestBuilder atLeastTimes(final int times) {
        this.atLeastTimes = times;
        return this;
    }

    public RequestBuilder wiremockPath(final String endpointPath) {
        if (nonNull(endpointPath)) {
            this.endpoint = endpointPath;
        }
        return this;
    }

    public RequestBuilder wiremockPath(final WireMockEndpoint endpoint, final Map<String, Object> parameters) {
        if (nonNull(endpoint)) {
            this.endpoint = PathTemplate.resolve(endpoint.getPath(), isNull(parameters) ? Map.of() : parameters);
        }
        return this;
    }

    public RequestBuilder jsonName(final String jsonName) {
        if (nonNull(jsonName)) {
            this.jsonValue = this.resourceLoader.getFromFile(jsonName);
        }
        return this;
    }

    public RequestBuilder ignoreFields(final String... fields) {
        if (nonNull(fields)) {
            Collections.addAll(this.ignoreFields, fields);
        }
        return this;
    }

    public RequestBuilder id(final UUID id) {
        if (nonNull(id)) {
            this.id = id;
        }
        return this;
    }

    public RequestBuilder cleanAfterVerify() {
        this.cleanAfterVerify = true;
        return this;
    }

    public WireMockCheck build() {
        return new WireMockCheck(this.endpoint, this.jsonValue, this.atLeastTimes, this.ignoreFields, this.cleanAfterVerify, this.id);
    }

    public void verify() {
        this.verifier.accept(this.build());
    }
}