package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
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
        when(this.agentProjectAtmssoxClient.getAgentProjects(1, 20)).thenReturn(mock(AgentProjectsPageResponse.class));

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
        final GetAgentRulesQuery query = mock(GetAgentRulesQuery.class);
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

    @Test
    void givenIds_whenGetProjectAndConversation_thenDelegateToDedicatedClients() {
        //given
        final UUID projectId = UUID.randomUUID();
        final UUID conversationId = UUID.randomUUID();
        final AgentProject project = mock(AgentProject.class);
        final AgentConversationDetails details = mock(AgentConversationDetails.class);
        when(this.agentProjectAtmssoxClient.getAgentProject(projectId)).thenReturn(project);
        when(this.agentConversationAtmssoxClient.getAgentConversation(conversationId)).thenReturn(details);

        //when
        final AgentProject actualProject = this.agentClient.getAgentProject(projectId);
        final AgentConversationDetails actualDetails = this.agentClient.getAgentConversation(conversationId);

        //then
        assertThat(actualProject).isEqualTo(project);
        assertThat(actualDetails).isEqualTo(details);
        verify(this.agentProjectAtmssoxClient).getAgentProject(projectId);
        verify(this.agentConversationAtmssoxClient).getAgentConversation(conversationId);
    }

    @Test
    void givenNoInput_whenGetAgents_thenDelegateToAgentClient() {
        //given
        final AgentsResponse response = mock(AgentsResponse.class);
        when(this.agentAtmssoxClient.getAgents()).thenReturn(response);

        //when
        final AgentsResponse actual = this.agentClient.getAgents();

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentAtmssoxClient).getAgents();
    }

    @Test
    void givenAgentId_whenGetActivateArchiveRestoreDelete_thenDelegateToAgentClient() {
        //given
        final UUID agentId = UUID.randomUUID();
        final Agent response = mock(Agent.class);
        when(this.agentAtmssoxClient.getAgent(agentId)).thenReturn(response);
        when(this.agentAtmssoxClient.activateAgent(agentId)).thenReturn(response);
        when(this.agentAtmssoxClient.archiveAgent(agentId)).thenReturn(response);
        when(this.agentAtmssoxClient.restoreAgent(agentId)).thenReturn(response);
        when(this.agentAtmssoxClient.deleteAgent(agentId)).thenReturn(response);

        //when
        this.agentClient.getAgent(agentId);
        this.agentClient.activateAgent(agentId);
        this.agentClient.archiveAgent(agentId);
        this.agentClient.restoreAgent(agentId);
        this.agentClient.deleteAgent(agentId);

        //then
        verify(this.agentAtmssoxClient).getAgent(agentId);
        verify(this.agentAtmssoxClient).activateAgent(agentId);
        verify(this.agentAtmssoxClient).archiveAgent(agentId);
        verify(this.agentAtmssoxClient).restoreAgent(agentId);
        verify(this.agentAtmssoxClient).deleteAgent(agentId);
    }

    @Test
    void givenConversationAgentId_whenGetConversations_thenDelegateToConversationClient() {
        //given
        final UUID agentId = UUID.randomUUID();
        final AgentConversationsResponse response = mock(AgentConversationsResponse.class);
        when(this.agentConversationAtmssoxClient.getAgentConversations(agentId)).thenReturn(response);

        //when
        final AgentConversationsResponse actual = this.agentClient.getAgentConversations(agentId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentConversationAtmssoxClient).getAgentConversations(agentId);
    }

    @Test
    void givenRuleRequests_whenMutateRules_thenDelegateToRuleClient() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final com.sitionix.bffssox.domain.CreateAgentRuleRequest createRequest = mock(com.sitionix.bffssox.domain.CreateAgentRuleRequest.class);
        final PatchAgentRuleRequest patchRequest = mock(PatchAgentRuleRequest.class);
        final com.sitionix.bffssox.domain.AcceptAgentRuleRequest acceptRequest = mock(com.sitionix.bffssox.domain.AcceptAgentRuleRequest.class);
        final AgentRule rule = mock(AgentRule.class);
        final DeleteAgentRuleResponse deleteResponse = mock(DeleteAgentRuleResponse.class);
        when(this.agentRuleAtmssoxClient.createAgentRule(agentId, createRequest)).thenReturn(rule);
        when(this.agentRuleAtmssoxClient.patchAgentRule(agentId, ruleId, patchRequest)).thenReturn(rule);
        when(this.agentRuleAtmssoxClient.acceptAgentRule(agentId, ruleId, acceptRequest)).thenReturn(rule);
        when(this.agentRuleAtmssoxClient.rejectAgentRule(agentId, ruleId)).thenReturn(rule);
        when(this.agentRuleAtmssoxClient.deleteAgentRule(agentId, ruleId)).thenReturn(deleteResponse);

        //when
        this.agentClient.createAgentRule(agentId, createRequest);
        this.agentClient.patchAgentRule(agentId, ruleId, patchRequest);
        this.agentClient.acceptAgentRule(agentId, ruleId, acceptRequest);
        this.agentClient.rejectAgentRule(agentId, ruleId);
        this.agentClient.deleteAgentRule(agentId, ruleId);

        //then
        verify(this.agentRuleAtmssoxClient).createAgentRule(agentId, createRequest);
        verify(this.agentRuleAtmssoxClient).patchAgentRule(agentId, ruleId, patchRequest);
        verify(this.agentRuleAtmssoxClient).acceptAgentRule(agentId, ruleId, acceptRequest);
        verify(this.agentRuleAtmssoxClient).rejectAgentRule(agentId, ruleId);
        verify(this.agentRuleAtmssoxClient).deleteAgentRule(agentId, ruleId);
    }
}
