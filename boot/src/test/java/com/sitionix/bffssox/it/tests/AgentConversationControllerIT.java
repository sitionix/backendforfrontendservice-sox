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
class AgentConversationControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given valid user token when get agent conversations then return conversations list")
    void givenValidUserToken_whenGetAgentConversations_thenReturnConversationsList() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_CONVERSATIONS)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_CONVERSATIONS)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when get agent conversations and downstream returns empty then return empty list")
    void givenValidUserToken_whenGetAgentConversationsAndDownstreamReturnsEmpty_thenReturnEmptyList() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_CONVERSATIONS)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .responseBody("responseDefaultMappingGetAgentConversationsEmpty.json")
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_CONVERSATIONS)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .expectResponse("responseDefaultGetAgentConversationsEmpty.json")
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given invalid agent id when get agent conversations then return bad request")
    void givenInvalidAgentId_whenGetAgentConversations_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_CONVERSATIONS)
                .withPathParameters(PathParams.create()
                        .add("agentId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultActivateAgentInvalidAgentId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when get agent conversations then return unauthorized")
    void givenMissingToken_whenGetAgentConversations_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_CONVERSATIONS)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given valid user token when get one agent conversation then return conversation details")
    void givenValidUserToken_whenGetOneAgentConversation_thenReturnConversationDetails() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_CONVERSATION)
                .pathPattern(WireMockPathParams.create()
                        .add("conversationId", "11111111-1111-1111-1111-111111111111"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_CONVERSATION)
                .withPathParameters(PathParams.create()
                        .add("conversationId", "11111111-1111-1111-1111-111111111111"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given invalid conversation id when get one agent conversation then return bad request")
    void givenInvalidConversationId_whenGetOneAgentConversation_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_CONVERSATION)
                .withPathParameters(PathParams.create()
                        .add("conversationId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultInvalidConversationId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given upstream not found when get one agent conversation then return not found with body")
    void givenUpstreamNotFound_whenGetOneAgentConversation_thenReturnNotFoundWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_AGENT_CONVERSATION)
                .pathPattern(WireMockPathParams.create()
                        .add("conversationId", "11111111-1111-1111-1111-111111111111"))
                .responseStatus(HttpStatus.NOT_FOUND)
                .responseBody("responseDefaultMappingGetAgentConversationNotFound.json")
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.GET_AGENT_CONVERSATION)
                .withPathParameters(PathParams.create()
                        .add("conversationId", "11111111-1111-1111-1111-111111111111"))
                .applyDefault(context -> context.expectStatus(HttpStatus.NOT_FOUND.value())
                        .expectResponse("responseDefaultGetAgentConversationNotFound.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given blank message when chat then return bad request")
    void givenBlankMessage_whenChat_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CHAT_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.withRequest("requestDefaultChatAgentBlankMessage.json")
                        .expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultChatAgentBlankMessage.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when chat then return unauthorized")
    void givenMissingToken_whenChat_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CHAT_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }
}
