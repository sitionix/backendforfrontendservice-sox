package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentClientImpl implements AgentClient {

    private final AgentAtmssoxClient agentAtmssoxClient;
    private final AgentProjectAtmssoxClient agentProjectAtmssoxClient;
    private final AgentConversationAtmssoxClient agentConversationAtmssoxClient;
    private final AgentRuleAtmssoxClient agentRuleAtmssoxClient;
    private final AgentChatAtmssoxClient agentChatAtmssoxClient;

    @Override
    public Agent createAgent(final CreateAgentRequest request) {
        return this.agentAtmssoxClient.createAgent(request);
    }

    @Override
    public AgentProject createAgentProject(final CreateAgentProjectRequest request) {
        return this.agentProjectAtmssoxClient.createAgentProject(request);
    }

    @Override
    public AgentsResponse getAgents() {
        return this.agentAtmssoxClient.getAgents();
    }

    @Override
    public AgentProjectsPageResponse getAgentProjects(final Integer page, final Integer size) {
        return this.agentProjectAtmssoxClient.getAgentProjects(page, size);
    }

    @Override
    public AgentProject getAgentProject(final UUID projectId) {
        return this.agentProjectAtmssoxClient.getAgentProject(projectId);
    }

    @Override
    public Agent getAgent(final UUID agentId) {
        return this.agentAtmssoxClient.getAgent(agentId);
    }

    @Override
    public AgentConversationsResponse getAgentConversations(final UUID agentId) {
        return this.agentConversationAtmssoxClient.getAgentConversations(agentId);
    }

    @Override
    public AgentConversationDetails getAgentConversation(final UUID conversationId) {
        return this.agentConversationAtmssoxClient.getAgentConversation(conversationId);
    }

    @Override
    public void deleteAgentConversation(final UUID conversationId) {
        this.agentConversationAtmssoxClient.deleteAgentConversation(conversationId);
    }

    @Override
    public Agent activateAgent(final UUID agentId) {
        return this.agentAtmssoxClient.activateAgent(agentId);
    }

    @Override
    public Agent archiveAgent(final UUID agentId) {
        return this.agentAtmssoxClient.archiveAgent(agentId);
    }

    @Override
    public Agent restoreAgent(final UUID agentId) {
        return this.agentAtmssoxClient.restoreAgent(agentId);
    }

    @Override
    public Agent deleteAgent(final UUID agentId) {
        return this.agentAtmssoxClient.deleteAgent(agentId);
    }

    @Override
    public AgentRulesResponse getAgentRules(final UUID agentId, final GetAgentRulesQuery query) {
        return this.agentRuleAtmssoxClient.getAgentRules(agentId, query);
    }

    @Override
    public AgentRule createAgentRule(final UUID agentId, final CreateAgentRuleRequest request) {
        return this.agentRuleAtmssoxClient.createAgentRule(agentId, request);
    }

    @Override
    public AgentRule patchAgentRule(final UUID agentId, final UUID ruleId, final PatchAgentRuleRequest request) {
        return this.agentRuleAtmssoxClient.patchAgentRule(agentId, ruleId, request);
    }

    @Override
    public AgentRule acceptAgentRule(final UUID agentId, final UUID ruleId, final AcceptAgentRuleRequest request) {
        return this.agentRuleAtmssoxClient.acceptAgentRule(agentId, ruleId, request);
    }

    @Override
    public AgentRule rejectAgentRule(final UUID agentId, final UUID ruleId) {
        return this.agentRuleAtmssoxClient.rejectAgentRule(agentId, ruleId);
    }

    @Override
    public DeleteAgentRuleResponse deleteAgentRule(final UUID agentId, final UUID ruleId) {
        return this.agentRuleAtmssoxClient.deleteAgentRule(agentId, ruleId);
    }

    @Override
    public SubmitChatExecutionResponse submitAgentChatExecution(final UUID agentId,
                                                                final ChatAgentRequest request,
                                                                final String idempotencyKey) {
        return this.agentChatAtmssoxClient.submitAgentChatExecution(agentId, request, idempotencyKey);
    }

    @Override
    public ChatExecution getAgentChatExecution(final UUID agentId, final UUID executionId, final UUID conversationId) {
        return this.agentChatAtmssoxClient.getAgentChatExecution(agentId, executionId, conversationId);
    }

    @Override
    public Agent patchAgent(final UUID agentId, final PatchAgentRequest request) {
        return this.agentAtmssoxClient.patchAgent(agentId, request);
    }
}
