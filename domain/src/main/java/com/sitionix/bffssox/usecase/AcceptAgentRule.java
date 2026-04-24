package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import java.util.UUID;

/**
 * Use case for accepting one automation rule through BFF.
 */
public interface AcceptAgentRule {

    /**
     * Accepts one rule for one agent.
     *
     * @param agentId agent identifier.
     * @param ruleId rule identifier.
     * @param request optional title/content update payload.
     * @return updated rule.
     */
    AgentRule execute(UUID agentId, UUID ruleId, AcceptAgentRuleRequest request);
}
