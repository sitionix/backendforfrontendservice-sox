package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveAgentFromProjectImpl implements RemoveAgentFromProject {

    private final AgentProjectClient agentProjectClient;

    @Override
    public void execute(final UUID projectId, final UUID agentId) {
        this.agentProjectClient.removeAgentFromProject(projectId, agentId);
    }
}
