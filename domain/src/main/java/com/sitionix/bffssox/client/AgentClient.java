package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import java.util.UUID;

/**
 * Downstream automation service client.
 */
public interface AgentClient {

    /**
     * Creates a new automation agent.
     *
     * @param request create payload.
     * @return created agent.
     */
    Agent createAgent(CreateAgentRequest request);

    /**
     * Returns current automation agents.
     *
     * @return persisted agents response.
     */
    AgentsResponse getAgents();

    /**
     * Returns one automation agent.
     *
     * @param agentId unique agent identifier.
     * @return persisted agent.
     */
    Agent getAgent(UUID agentId);

    /**
     * Returns direct conversations for one automation agent.
     *
     * @param agentId unique agent identifier.
     * @return conversations list response.
     */
    AgentConversationsResponse getAgentConversations(UUID agentId);

    /**
     * Returns one direct conversation with ordered message history.
     *
     * @param conversationId unique conversation identifier.
     * @return conversation details response.
     */
    AgentConversationDetails getAgentConversation(UUID conversationId);

    /**
     * Activates one automation agent.
     *
     * @param agentId unique agent identifier.
     * @return updated agent.
     */
    Agent activateAgent(UUID agentId);

    /**
     * Archives one automation agent.
     *
     * @param agentId unique agent identifier.
     * @return updated agent.
     */
    Agent archiveAgent(UUID agentId);

    /**
     * Restores one archived automation agent.
     *
     * @param agentId unique agent identifier.
     * @return updated agent.
     */
    Agent restoreAgent(UUID agentId);

    /**
     * Soft deletes one automation agent.
     *
     * @param agentId unique agent identifier.
     * @return updated agent.
     */
    Agent deleteAgent(UUID agentId);

    AgentRulesResponse getAgentRules(UUID agentId, GetAgentRulesQuery query);

    AgentRule createAgentRule(UUID agentId, CreateAgentRuleRequest request);

    AgentRule patchAgentRule(UUID agentId, UUID ruleId, PatchAgentRuleRequest request);

    AgentRule acceptAgentRule(UUID agentId, UUID ruleId, AcceptAgentRuleRequest request);

    AgentRule rejectAgentRule(UUID agentId, UUID ruleId);

    DeleteAgentRuleResponse deleteAgentRule(UUID agentId, UUID ruleId);

    /**
     * Executes one chat request for one automation agent.
     *
     * @param agentId unique agent identifier.
     * @param request chat request payload.
     * @return assistant reply payload.
     */
    ChatAgentResponse chatAgent(UUID agentId, ChatAgentRequest request);

    /**
     * Applies partial identity update for one automation agent.
     *
     * @param agentId unique agent identifier.
     * @param request partial update payload.
     * @return updated agent.
     */
    Agent patchAgent(UUID agentId, PatchAgentRequest request);
}
