package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectClient;
import com.sitionix.bffssox.domain.AgentProjectFlowResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentProjectFlowImpl implements GetAgentProjectFlow {

    private final AgentProjectClient agentProjectClient;

    @Override
    public AgentProjectFlowResponse execute(final UUID projectId) {
        return this.agentProjectClient.getAgentProjectFlow(projectId);
    }
}
