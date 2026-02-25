package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@IntegrationTest(preparations = AuthJwksDataPreparation.class)
class SiteControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("Should create site when valid user token is provided")
    void givenValidUserToken_whenCreateSite_thenReturnCreatedSite() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_CREATE_SITE)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_SITE)
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return unauthorized when create site is called without user token")
    void givenMissingUserToken_whenCreateSite_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_SITE)
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultCreateSiteUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("Should return unauthorized with body when create site request is not authorized upstream")
    void givenUnauthorizedCreateSiteRequest_whenCreateSite_thenReturnUnauthorizedWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_CREATE_SITE)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingCreateSiteUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_SITE)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultCreateSiteUnauthorizedUpstream.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return service unavailable with body when site service fails")
    void givenUpstreamServerError_whenCreateSite_thenReturnServiceUnavailableWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_CREATE_SITE)
                .applyDefault(context -> context.responseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .responseBody("responseDefaultMappingCreateSiteServerError.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_SITE)
                .applyDefault(context -> context.expectStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .expectResponse("responseDefaultCreateSiteBadGateway.json"))
                .assertDefault();

        requestBuilder.verify();
    }
}
