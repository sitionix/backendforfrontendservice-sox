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
class AgentRuleControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given valid user token when get agent rules then return rules list")
    void givenValidUserToken_whenGetAgentRules_thenReturnRulesList() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_RULES)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_RULES)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when get agent rules and downstream returns empty then return empty list")
    void givenValidUserToken_whenGetAgentRulesAndDownstreamReturnsEmpty_thenReturnEmptyList() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_RULES)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .responseBody("responseDefaultMappingGetAgentRulesEmpty.json")
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_RULES)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .expectResponse("responseDefaultGetAgentRulesEmpty.json")
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when create agent rule then return created rule")
    void givenValidUserToken_whenCreateAgentRule_thenReturnCreatedRule() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_CREATE_AGENT_RULE)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_AGENT_RULE)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when patch agent rule then return updated rule")
    void givenValidUserToken_whenPatchAgentRule_thenReturnUpdatedRule() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.PATCH_AGENT_RULE)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                        .add("ruleId", "9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.PATCH_AGENT_RULE)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                        .add("ruleId", "9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when delete agent rule then return deleted status")
    void givenValidUserToken_whenDeleteAgentRule_thenReturnDeletedStatus() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.DELETE_AGENT_RULE)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                        .add("ruleId", "9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.DELETE_AGENT_RULE)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                        .add("ruleId", "9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when delete agent rule twice then return deleted status and forward twice")
    void givenValidUserToken_whenDeleteAgentRuleTwice_thenReturnDeletedStatusAndForwardTwice() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.DELETE_AGENT_RULE)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                        .add("ruleId", "9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.DELETE_AGENT_RULE)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                        .add("ruleId", "9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .assertDefault();

        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.DELETE_AGENT_RULE)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                        .add("ruleId", "9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .assertDefault();

        requestBuilder.atLeastTimes(2).verify();
    }

    @Test
    @DisplayName("given invalid agent id when get agent rules then return bad request")
    void givenInvalidAgentId_whenGetAgentRules_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_RULES)
                .withPathParameters(PathParams.create()
                        .add("agentId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultActivateAgentInvalidAgentId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given invalid agent id when create agent rule then return bad request")
    void givenInvalidAgentId_whenCreateAgentRule_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CREATE_AGENT_RULE)
                .withPathParameters(PathParams.create()
                        .add("agentId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultActivateAgentInvalidAgentId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when get agent rules then return unauthorized and do not call upstream")
    void givenMissingToken_whenGetAgentRules_thenReturnUnauthorizedAndDoNotCallUpstream() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_RULES)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }
}
