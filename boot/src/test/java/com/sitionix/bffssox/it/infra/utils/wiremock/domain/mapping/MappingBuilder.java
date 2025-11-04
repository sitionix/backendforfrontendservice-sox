package com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping;

import com.sitionix.bffssox.it.infra.utils.loader.RequestMappingResources;
import com.sitionix.bffssox.it.infra.utils.loader.ResponseMappingResources;
import com.sitionix.bffssox.it.infra.utils.wiremock.WireMockJournal;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.RequestBuilder;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.WireMockEndpoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public class MappingBuilder {

    private final RequestMappingResources requestMappingsResources;
    private final ResponseMappingResources responseMappingsResources;
    private final StubMappingRequest.StubMappingRequestBuilder stub;
    private final RequestSpec.RequestSpecBuilder req;
    private final ResponseSpec.ResponseSpecBuilder res;
    private final List<BodyPattern> bodyPatterns;
    private final Function<StubMappingRequest, UUID> mappingCreator;
    private final WireMockJournal journal;

    private String jsonFileName;
    private String endpoint;

    public MappingBuilder(final RequestMappingResources requestMappingsResources,
                          final ResponseMappingResources responseMappingsResources,
                          final WireMockJournal journal,
                          final Function<StubMappingRequest, UUID> mappingCreator) {
        this.journal = journal;
        this.mappingCreator = mappingCreator;
        this.requestMappingsResources = requestMappingsResources;
        this.responseMappingsResources = responseMappingsResources;
        this.stub = StubMappingRequest.builder();
        this.req = RequestSpec.builder();
        this.res = ResponseSpec.builder();
        this.bodyPatterns = new ArrayList<>();
    }

    public MappingBuilder name(final String name) {
        if (nonNull(name)) this.stub.name(name);
        return this;
    }

    public MappingBuilder matchesJson(final String requestFileName) {
        if (nonNull(requestFileName)) {
            this.jsonFileName = requestFileName;
            final String json = this.requestMappingsResources.getFromFile(requestFileName);
            final BodyPattern bp = BodyPattern.builder()
                    .equalToJson(json)
                    .ignoreArrayOrder(true)
                    .ignoreExtraElements(true)
                    .build();
            this.bodyPatterns.add(bp);
        }
        return this;
    }

    public MappingBuilder url(final String path) {
        if (nonNull(path)) {
            this.endpoint = path;
            this.req.url(path);
        }
        return this;
    }

    public MappingBuilder urlPath(final WireMockEndpoint path) {
        if (nonNull(path)) {
            this.endpoint = path.getPath();
            this.req.urlPath(path.getPath());
        }
        return this;
    }

    public MappingBuilder url(final String path, final Map<String, QueryParamPattern> parameters) {
        if (nonNull(path) && nonNull(parameters)) {
            this.endpoint = path;
            this.req.url(path);
            this.req.queryParameters(parameters);
        }
        return this;
    }

    public MappingBuilder method(final HttpMethod method) {
        if (nonNull(method)) this.req.method(method.name());
        return this;
    }

    public MappingBuilder responseStatus(final HttpStatus status) {
        if (nonNull(status)) this.res.status(status.value());
        return this;
    }

    public MappingBuilder responseBody(final String responseFileName) {
        if (nonNull(responseFileName)) {
            final String json = this.responseMappingsResources.getFromFile(responseFileName);
            this.res.body(json).headers(Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        }
        return this;
    }

    public MappingBuilder responseBodyFile(final String fileName) {
        if (nonNull(fileName)) {
            this.res.bodyFileName(fileName).headers(Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        }
        return this;
    }

    public RequestBuilder create() {
        final StubMappingRequest payload = this.stub
                .request(this.req.bodyPatterns(this.bodyPatterns)
                        .headers(Map.of(HttpHeaders.CONTENT_TYPE,
                                HeaderPattern.equalTo(MediaType.APPLICATION_JSON)))
                        .build())
                .response(this.res.build())
                .persistent(false)
                .build();

        final UUID id = this.mappingCreator.apply(payload);

        return this.journal.check()
                .id(id)
                .wiremockPath(this.endpoint)
                .jsonName(this.jsonFileName)
                .cleanAfterVerify();
    }
}