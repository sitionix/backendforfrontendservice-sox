package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentOperationsPort;
import com.sitionix.bffssox.domain.Agent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateAgentImpl implements ActivateAgent {

    private final AgentOperationsPort agentClient;

    @Override
    public Agent execute(final UUID agentId) {
        return this.agentClient.activateAgent(agentId);
    }
}
