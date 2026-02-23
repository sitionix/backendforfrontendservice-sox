package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.mockmvc.api.QueryParams;
import com.sitionix.forgeit.wiremock.api.WireMockQueryParams;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@IntegrationTest(preparations = AuthJwksDataPreparation.class)
class SiteQueryControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given valid user token when get sites first page then return sites page")
    void givenValidUserToken_whenGetSitesFirstPage_thenReturnSitesPage() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_SITES)
                .urlWithQueryParam(WireMockQueryParams.create()
                        .add("page", 0)
                        .add("size", 20))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", 0)
                        .add("size", 20))
                .applyDefault(context -> context.expectResponse("responseDefaultGetSitesFirstPageWithHappyPath.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when get sites next page then return sites page")
    void givenValidUserToken_whenGetSitesNextPage_thenReturnSitesPage() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_SITES)
                .urlWithQueryParam(WireMockQueryParams.create()
                        .add("page", 1)
                        .add("size", 20))
                .applyDefault(context -> context.responseBody("responseDefaultMappingGetSitesNextPageWithHappyPath.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", 1)
                        .add("size", 20))
                .applyDefault(context -> context.expectResponse("responseDefaultGetSitesNextPageWithHappyPath.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when get sites end of list then return has next false")
    void givenValidUserToken_whenGetSitesEndOfList_thenReturnHasNextFalse() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_SITES)
                .urlWithQueryParam(WireMockQueryParams.create()
                        .add("page", 1)
                        .add("size", 20))
                .applyDefault(context -> context.responseBody("responseDefaultMappingGetSitesEndOfListWithHappyPath.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", 1)
                        .add("size", 20))
                .applyDefault(context -> context.expectResponse("responseDefaultGetSitesEndOfListWithHappyPath.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given invalid size when get sites then return bad request")
    void givenInvalidSize_whenGetSites_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", 0)
                        .add("size", 0))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value()))
                .assertDefault();
    }

    @Test
    @DisplayName("given negative page when get sites then return bad request")
    void givenNegativePage_whenGetSites_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", -1)
                        .add("size", 20))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value()))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when get sites then return unauthorized")
    void givenMissingToken_whenGetSites_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", 0)
                        .add("size", 20))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }
}
