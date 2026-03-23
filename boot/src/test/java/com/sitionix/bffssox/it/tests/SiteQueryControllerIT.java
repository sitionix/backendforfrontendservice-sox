package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.mockmvc.api.PathParams;
import com.sitionix.forgeit.mockmvc.api.QueryParams;
import com.sitionix.forgeit.wiremock.api.WireMockPathParams;
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
                .expectResponse("responseDefaultGetSitesFirstPageWithHappyPath.json")
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
                .responseBody("responseDefaultMappingGetSitesNextPageWithHappyPath.json")
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", 1)
                        .add("size", 20))
                .expectResponse("responseDefaultGetSitesNextPageWithHappyPath.json")
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
                .responseBody("responseDefaultMappingGetSitesEndOfListWithHappyPath.json")
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITES)
                .withQueryParameters(QueryParams.create()
                        .add("page", 1)
                        .add("size", 20))
                .expectResponse("responseDefaultGetSitesEndOfListWithHappyPath.json")
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
                .expectStatus(HttpStatus.BAD_REQUEST)
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
                .expectStatus(HttpStatus.BAD_REQUEST)
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

    @Test
    @DisplayName("given valid user token when get site overview then return site overview")
    void givenValidUserToken_whenGetSiteOverview_thenReturnSiteOverview() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_SITE_OVERVIEW)
                .pathPattern(WireMockPathParams.create()
                        .add("siteId", "c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITE_OVERVIEW)
                .withPathParameters(PathParams.create()
                        .add("siteId", "c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .expectResponse("responseDefaultGetSiteOverviewWithHappyPath.json")
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given invalid site id when get site overview then return bad request")
    void givenInvalidSiteId_whenGetSiteOverview_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITE_OVERVIEW)
                .withPathParameters(PathParams.create()
                        .add("siteId", "not-a-valid-id"))
                .expectStatus(HttpStatus.BAD_REQUEST)
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when get site overview then return unauthorized")
    void givenMissingToken_whenGetSiteOverview_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITE_OVERVIEW)
                .withPathParameters(PathParams.create()
                        .add("siteId", "c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given upstream not found when get site overview then return not found with body")
    void givenUpstreamNotFound_whenGetSiteOverview_thenReturnNotFoundWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_SITE_OVERVIEW)
                .pathPattern(WireMockPathParams.create()
                        .add("siteId", "c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .responseStatus(HttpStatus.NOT_FOUND)
                .responseBody("responseDefaultMappingGetSiteOverviewNotFound.json")
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_SITE_OVERVIEW)
                .withPathParameters(PathParams.create()
                        .add("siteId", "c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .applyDefault(context -> context.expectStatus(HttpStatus.NOT_FOUND.value())
                        .expectResponse("responseDefaultGetSiteOverviewNotFound.json"))
                .assertDefault();

        requestBuilder.verify();
    }
}
