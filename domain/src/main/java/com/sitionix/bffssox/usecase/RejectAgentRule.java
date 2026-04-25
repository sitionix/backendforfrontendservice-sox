package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentRule;
import java.util.UUID;

/**
 * Use case for rejecting one suggested automation rule through BFF.
 */
public interface RejectAgentRule {

    /**
     * Rejects one rule for one agent.
     *
     * @param agentId agent identifier.
     * @param ruleId rule identifier.
     * @return updated rule.
     */
    AgentRule execute(UUID agentId, UUID ruleId);
}
