package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentRulesImpl implements GetAgentRules {

    private final AgentClient agentClient;

    @Override
    public AgentRulesResponse execute(final UUID agentId, final GetAgentRulesQuery query) {
        return this.agentClient.getAgentRules(agentId, query);
    }
}
