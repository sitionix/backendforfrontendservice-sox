package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentsImpl implements GetAgents {

    private final AgentClient agentClient;

    @Override
    public AgentsResponse execute() {
        return this.agentClient.getAgents();
    }
}
