package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchAgentImpl implements PatchAgent {

    private final AgentClient agentClient;

    @Override
    public Agent execute(final UUID agentId, final PatchAgentRequest request) {
        return this.agentClient.patchAgent(agentId, request);
    }
}
