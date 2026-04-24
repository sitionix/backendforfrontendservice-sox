package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import java.util.UUID;

public interface CreateAgentRule {

    AgentRule execute(UUID agentId, CreateAgentRuleRequest request);
}
