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
class AgentProjectControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given valid user token when create agent project then return created project")
    void givenValidUserToken_whenCreateAgentProject_thenReturnCreatedProject() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_CREATE_AGENT_PROJECT)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_AGENT_PROJECT)
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when get agent projects then return projects page")
    void givenValidUserToken_whenGetAgentProjects_thenReturnProjectsPage() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_PROJECTS)
                .urlWithQueryParam(WireMockQueryParams.create()
                        .add("page", 0)
                        .add("size", 20))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_PROJECTS)
                .withQueryParameters(QueryParams.create()
                        .add("page", 0)
                        .add("size", 20))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given missing token when create agent project then return unauthorized")
    void givenMissingToken_whenCreateAgentProject_thenReturnUnauthorized() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_AGENT_PROJECT)
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given blank project name when create agent project then return bad request")
    void givenBlankProjectName_whenCreateAgentProject_thenReturnBadRequest() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_AGENT_PROJECT)
                .applyDefault(context -> context.withRequest("requestDefaultCreateAgentProjectBlankName.json")
                        .expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultCreateAgentProjectBlankName.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when get agent projects then return unauthorized")
    void givenMissingToken_whenGetAgentProjects_thenReturnUnauthorized() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_PROJECTS)
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }
}
