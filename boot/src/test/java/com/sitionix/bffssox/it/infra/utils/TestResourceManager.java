package com.sitionix.bffssox.it.infra.utils;

import com.sitionix.bffssox.it.infra.utils.loader.ExpectedResources;
import com.sitionix.bffssox.it.infra.utils.loader.GivenRequestResources;
import com.sitionix.bffssox.it.infra.utils.wiremock.WireMockJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestResourceManager {

    @Autowired
    private WireMockJournal wireMockJournal;

    @Autowired
    private GivenRequestResources givenRequestResources;

    @Autowired
    private ExpectedResources expectedResources;

    public ExpectedResources expected() {
        return this.expectedResources;
    }

    public GivenRequestResources request() {
        return this.givenRequestResources;
    }

    public WireMockJournal wireMockJournal() {
        return this.wireMockJournal;
    }
}
