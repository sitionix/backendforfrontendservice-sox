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
    @DisplayName("given valid user token when get agent project then return project")
    void givenValidUserToken_whenGetAgentProject_thenReturnProject() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_PROJECT)
                .pathPattern(WireMockPathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when patch agent project then proxy patch and return updated project")
    void givenValidUserToken_whenPatchAgentProject_thenProxyPatchAndReturnProject() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.PATCH_AGENT_PROJECT)
                .pathPattern(WireMockPathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectResponse("responseDefaultPatchAgentProjectNameOnly.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when delete agent project then proxy delete and return no content")
    void givenValidUserToken_whenDeleteAgentProject_thenProxyDeleteAndReturnNoContent() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.DELETE_AGENT_PROJECT)
                .pathPattern(WireMockPathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.DELETE_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given upstream bad request when patch agent project then return bad request")
    void givenUpstreamBadRequest_whenPatchAgentProject_thenReturnBadRequest() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.PATCH_AGENT_PROJECT)
                .pathPattern(WireMockPathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.responseStatus(HttpStatus.BAD_REQUEST.value())
                        .responseBody("responseDefaultMappingPatchAgentProjectBadRequest.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responsePatchAgentProjectBadRequest.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given upstream not found when patch agent project then return not found")
    void givenUpstreamNotFound_whenPatchAgentProject_thenReturnNotFound() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.PATCH_AGENT_PROJECT)
                .pathPattern(WireMockPathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.responseStatus(HttpStatus.NOT_FOUND.value())
                        .responseBody("responseDefaultMappingPatchAgentProjectNotFound.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectStatus(HttpStatus.NOT_FOUND.value())
                        .expectResponse("responseDefaultPatchAgentProjectNotFound.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given upstream not found when delete agent project then return not found")
    void givenUpstreamNotFound_whenDeleteAgentProject_thenReturnNotFound() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.DELETE_AGENT_PROJECT)
                .pathPattern(WireMockPathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.responseStatus(HttpStatus.NOT_FOUND.value())
                        .responseBody("responseDefaultMappingPatchAgentProjectNotFound.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.DELETE_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectStatus(HttpStatus.NOT_FOUND.value())
                        .expectResponse("responseDefaultPatchAgentProjectNotFound.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given unsupported field patch request when patch agent project then proxy bad request")
    void givenUnsupportedFieldPatchRequest_whenPatchAgentProject_thenProxyBadRequest() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .withRequest("requestDefaultPatchAgentProjectUnsupportedField.json"))
                .assertDefault();
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
    @DisplayName("given invalid project id when get agent project then return bad request")
    void givenInvalidProjectId_whenGetAgentProject_thenReturnBadRequest() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultGetAgentProjectInvalidProjectId.json"))
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

    @Test
    @DisplayName("given missing token when get agent project then return unauthorized")
    void givenMissingToken_whenGetAgentProject_thenReturnUnauthorized() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when patch agent project then return unauthorized and skip downstream call")
    void givenMissingToken_whenPatchAgentProject_thenReturnUnauthorized() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when delete agent project then return unauthorized and skip downstream call")
    void givenMissingToken_whenDeleteAgentProject_thenReturnUnauthorized() {
        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.DELETE_AGENT_PROJECT)
                .withPathParameters(PathParams.create()
                        .add("projectId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }
}
