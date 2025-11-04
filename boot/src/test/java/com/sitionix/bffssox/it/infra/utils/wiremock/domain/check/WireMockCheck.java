package com.sitionix.bffssox.it.infra.utils.wiremock.domain.check;

import java.util.List;
import java.util.UUID;

public record WireMockCheck(
        String endpoint,
        String expectedJson,
        int atLeastTimes,
        List<String> ignoreFields,
        boolean cleanAfterVerify,
        UUID id) {}
