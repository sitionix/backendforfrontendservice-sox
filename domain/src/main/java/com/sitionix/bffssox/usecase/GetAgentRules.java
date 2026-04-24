package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import java.util.UUID;

public interface GetAgentRules {

    AgentRulesResponse execute(UUID agentId, GetAgentRulesQuery query);
}
