package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.Agent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestoreAgentImpl implements RestoreAgent {

    private final AgentClient agentClient;

    @Override
    public Agent execute(final UUID agentId) {
        return this.agentClient.restoreAgent(agentId);
    }
}
