package com.sitionix.bffssox.it.infra.utils.wiremock;

import com.sitionix.bffssox.it.infra.utils.loader.ExpectedResources;
import com.sitionix.bffssox.it.infra.utils.loader.RequestMappingResources;
import com.sitionix.bffssox.it.infra.utils.loader.ResponseMappingResources;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.RequestBuilder;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.WireMockCheck;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping.MappingBuilder;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.mapping.StubMappingRequest;
import com.sitionix.bffssox.it.infra.utils.wiremock.verify.WireMockValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WireMockJournal {

    private final ExpectedResources expectedResources;
    private final RequestMappingResources requestMappingsResources;
    private final ResponseMappingResources responseMappingsResources;
    private final WireMockValidator validator;
    private final WireMockJournalClient journalClient;

    public void reset() {
        this.journalClient.reset();
    }

    public void deleteMapping(final UUID id) {
        this.journalClient.deleteMapping(id);
    }

    public RequestBuilder check() {
        return new RequestBuilder(this.requestMappingsResources, this::verify);
    }

    public MappingBuilder createMapping() {
        return new MappingBuilder(this.requestMappingsResources, this.responseMappingsResources, this, this::verify);
    }

    private void verify(final WireMockCheck check) {
        this.validator.validate(check);
    }

    private UUID verify(final StubMappingRequest request) {
        return this.journalClient.createMapping(request);
    }
}