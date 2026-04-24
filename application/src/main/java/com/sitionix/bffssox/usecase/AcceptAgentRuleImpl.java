package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcceptAgentRuleImpl implements AcceptAgentRule {

    private final AgentClient agentClient;

    @Override
    public AgentRule execute(final UUID agentId, final UUID ruleId, final AcceptAgentRuleRequest request) {
        return this.agentClient.acceptAgentRule(agentId, ruleId, request);
    }
}
