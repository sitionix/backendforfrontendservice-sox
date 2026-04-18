package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.mockmvc.api.PathParams;
import com.sitionix.forgeit.wiremock.api.WireMockPathParams;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@IntegrationTest(preparations = AuthJwksDataPreparation.class)
class AgentDefinitionControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given valid user token when create agent with name only then return created agent")
    void givenValidUserToken_whenCreateAgentWithNameOnly_thenReturnCreatedAgent() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_CREATE_AGENT)
                .createDefault(context -> context.mutateRequest(request -> request.setDescription(null)));

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_AGENT)
                .assertDefault(context -> context.mutateRequest(request -> request.setDescription(null)));

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when get agent by id then return agent with instruction")
    void givenValidUserToken_whenGetAgentById_thenReturnAgentWithInstruction() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when patch agent with instruction only then return updated agent")
    void givenValidUserToken_whenPatchAgentWithInstructionOnly_thenReturnUpdatedAgent() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.PATCH_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault(context -> context.mutateRequest(request -> {
                    request.setName(null);
                    request.setDescription(null);
                }));

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault(context -> context.mutateRequest(request -> {
                    request.setName(null);
                    request.setDescription(null);
                }));

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when patch agent with description only then return updated agent")
    void givenValidUserToken_whenPatchAgentWithDescriptionOnly_thenReturnUpdatedAgent() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.PATCH_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault(context -> context.mutateRequest(request -> {
                    request.setName(null);
                    request.setInstruction(null);
                    request.setDescription("Updated architecture-focused description");
                }));

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault(context -> context.mutateRequest(request -> {
                    request.setName(null);
                    request.setInstruction(null);
                    request.setDescription("Updated architecture-focused description");
                }));

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when patch agent with name and instruction then return updated agent")
    void givenValidUserToken_whenPatchAgentWithNameAndInstruction_thenReturnUpdatedAgent() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.PATCH_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault(context -> context.mutateRequest(request -> {
                    request.setName("Principal Architecture Reviewer");
                    request.setDescription(null);
                    request.setInstruction("Prioritize instruction consistency across BFF and upstream");
                }));

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault(context -> context.mutateRequest(request -> {
                    request.setName("Principal Architecture Reviewer");
                    request.setDescription(null);
                    request.setInstruction("Prioritize instruction consistency across BFF and upstream");
                }));

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given empty patch body when patch agent then return bad request")
    void givenEmptyPatchBody_whenPatchAgent_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.withRequest("requestDefaultPatchAgentEmptyBody.json")
                        .expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultPatchAgentEmptyBody.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given invalid agent id when get agent then return bad request")
    void givenInvalidAgentId_whenGetAgent_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultActivateAgentInvalidAgentId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given invalid agent id when patch agent then return bad request")
    void givenInvalidAgentId_whenPatchAgent_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultActivateAgentInvalidAgentId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when get agent then return unauthorized")
    void givenMissingToken_whenGetAgent_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }
}
