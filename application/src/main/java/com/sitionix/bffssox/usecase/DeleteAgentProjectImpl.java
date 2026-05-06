package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAgentProjectImpl implements DeleteAgentProject {

    private final AgentProjectClient agentClient;

    @Override
    public void execute(final UUID projectId) {
        this.agentClient.deleteAgentProject(projectId);
    }
}
