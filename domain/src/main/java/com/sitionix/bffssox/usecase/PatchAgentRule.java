package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import java.util.UUID;

public interface PatchAgentRule {

    AgentRule execute(UUID agentId, UUID ruleId, PatchAgentRuleRequest request);
}
