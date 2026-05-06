package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentRuleOperationsPort;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchAgentRuleImpl implements PatchAgentRule {

    private final AgentRuleOperationsPort agentClient;

    @Override
    public AgentRule execute(final UUID agentId, final UUID ruleId, final PatchAgentRuleRequest request) {
        return this.agentClient.patchAgentRule(agentId, ruleId, request);
    }
}
