package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentClientImplTest {

    private AgentClientImpl agentClient;

    @Mock
    private AgentAtmssoxClient agentAtmssoxClient;
    @Mock
    private AgentProjectAtmssoxClient agentProjectAtmssoxClient;
    @Mock
    private AgentConversationAtmssoxClient agentConversationAtmssoxClient;
    @Mock
    private AgentRuleAtmssoxClient agentRuleAtmssoxClient;
    @Mock
    private AgentChatAtmssoxClient agentChatAtmssoxClient;

    @BeforeEach
    void setUp() {
        this.agentClient = new AgentClientImpl(
                this.agentAtmssoxClient,
                this.agentProjectAtmssoxClient,
                this.agentConversationAtmssoxClient,
                this.agentRuleAtmssoxClient,
                this.agentChatAtmssoxClient
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.agentAtmssoxClient,
                this.agentProjectAtmssoxClient,
                this.agentConversationAtmssoxClient,
                this.agentRuleAtmssoxClient,
                this.agentChatAtmssoxClient
        );
    }

    @Test
    void givenCreateRequest_whenCreateAgent_thenDelegateToAgentClient() {
        //given
        final CreateAgentRequest request = mock(CreateAgentRequest.class);
        final Agent expected = mock(Agent.class);
        when(this.agentAtmssoxClient.createAgent(request)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.createAgent(request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentAtmssoxClient).createAgent(request);
    }

    @Test
    void givenProjectPaging_whenGetAgentProjects_thenDelegateToProjectClient() {
        //given
        when(this.agentProjectAtmssoxClient.getAgentProjects(1, 20)).thenReturn(mock(com.sitionix.bffssox.domain.AgentProjectsPageResponse.class));

        //when
        this.agentClient.getAgentProjects(1, 20);

        //then
        verify(this.agentProjectAtmssoxClient).getAgentProjects(1, 20);
    }

    @Test
    void givenConversationId_whenDeleteAgentConversation_thenDelegateToConversationClient() {
        //given
        final UUID conversationId = UUID.fromString("cab3fa9e-c59f-47cb-8627-1c19698ef5f3");

        //when
        this.agentClient.deleteAgentConversation(conversationId);

        //then
        verify(this.agentConversationAtmssoxClient).deleteAgentConversation(conversationId);
    }

    @Test
    void givenQuery_whenGetAgentRules_thenDelegateToRuleClient() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final com.sitionix.bffssox.domain.GetAgentRulesQuery query = mock(com.sitionix.bffssox.domain.GetAgentRulesQuery.class);
        when(this.agentRuleAtmssoxClient.getAgentRules(agentId, query)).thenReturn(mock(com.sitionix.bffssox.domain.AgentRulesResponse.class));

        //when
        this.agentClient.getAgentRules(agentId, query);

        //then
        verify(this.agentRuleAtmssoxClient).getAgentRules(agentId, query);
    }

    @Test
    void givenChatRequest_whenSubmitChatExecution_thenDelegateToChatClient() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final com.sitionix.bffssox.domain.ChatAgentRequest request = mock(com.sitionix.bffssox.domain.ChatAgentRequest.class);
        when(this.agentChatAtmssoxClient.submitAgentChatExecution(agentId, request, "idempotency")).thenReturn(mock(com.sitionix.bffssox.domain.SubmitChatExecutionResponse.class));

        //when
        this.agentClient.submitAgentChatExecution(agentId, request, "idempotency");

        //then
        verify(this.agentChatAtmssoxClient).submitAgentChatExecution(agentId, request, "idempotency");
    }

    @Test
    void givenPatchRequest_whenPatchAgent_thenDelegateToAgentClient() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final com.sitionix.bffssox.domain.PatchAgentRequest request = mock(com.sitionix.bffssox.domain.PatchAgentRequest.class);
        when(this.agentAtmssoxClient.patchAgent(agentId, request)).thenReturn(mock(Agent.class));

        //when
        this.agentClient.patchAgent(agentId, request);

        //then
        verify(this.agentAtmssoxClient).patchAgent(agentId, request);
    }
}
