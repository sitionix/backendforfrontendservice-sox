package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentRule;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RejectAgentRuleImpl implements RejectAgentRule {

    private final AgentClient agentClient;

    @Override
    public AgentRule execute(final UUID agentId, final UUID ruleId) {
        return this.agentClient.rejectAgentRule(agentId, ruleId);
    }
}
