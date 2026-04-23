package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAgentRuleImpl implements DeleteAgentRule {

    private final AgentClient agentClient;

    @Override
    public DeleteAgentRuleResponse execute(final UUID agentId, final UUID ruleId) {
        return this.agentClient.deleteAgentRule(agentId, ruleId);
    }
}
