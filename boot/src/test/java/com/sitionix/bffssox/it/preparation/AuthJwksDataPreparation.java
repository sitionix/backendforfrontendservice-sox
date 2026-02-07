package com.sitionix.bffssox.it.preparation;

import com.sitionix.bffssox.it.tests.TestManager;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.domain.preparation.DataPreparation;
import org.springframework.http.HttpStatus;

public class AuthJwksDataPreparation implements DataPreparation<TestManager> {

    @Override
    public void prepare(final TestManager forgeit) {
        forgeit.wiremock()
                .createMapping(WireMockEndpoint.GET_JWKS)
                .responseBody("responseDefaultMappingJwks.json")
                .responseStatus(HttpStatus.OK)
                .plainUrl()
                .create();
    }
}
