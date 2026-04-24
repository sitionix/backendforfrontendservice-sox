package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import java.util.UUID;

public interface DeleteAgentRule {

    DeleteAgentRuleResponse execute(UUID agentId, UUID ruleId);
}
