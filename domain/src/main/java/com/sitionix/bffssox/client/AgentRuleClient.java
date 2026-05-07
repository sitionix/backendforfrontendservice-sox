package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import java.util.UUID;

/**
 * Port for automation agent rule operations.
 */
public interface AgentRuleClient {

    AgentRulesResponse getAgentRules(UUID agentId, GetAgentRulesQuery query);

    AgentRule createAgentRule(UUID agentId, CreateAgentRuleRequest request);

    AgentRule patchAgentRule(UUID agentId, UUID ruleId, PatchAgentRuleRequest request);

    AgentRule acceptAgentRule(UUID agentId, UUID ruleId, AcceptAgentRuleRequest request);

    AgentRule rejectAgentRule(UUID agentId, UUID ruleId);

    DeleteAgentRuleResponse deleteAgentRule(UUID agentId, UUID ruleId);
}
