package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentRuleClient;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAgentRuleImpl implements CreateAgentRule {

    private final AgentRuleClient agentClient;

    @Override
    public AgentRule execute(final UUID agentId, final CreateAgentRuleRequest request) {
        return this.agentClient.createAgentRule(agentId, request);
    }
}
