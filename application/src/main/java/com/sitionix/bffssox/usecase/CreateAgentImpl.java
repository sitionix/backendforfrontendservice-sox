package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAgentImpl implements CreateAgent {

    private final AgentClient agentClient;

    @Override
    public Agent execute(final CreateAgentRequest request) {
        return this.agentClient.createAgent(request);
    }
}
