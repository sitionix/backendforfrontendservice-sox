package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectClient;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddAgentToProjectImpl implements AddAgentToProject {

    private final AgentProjectClient agentProjectClient;

    @Override
    public ProjectAgent execute(final UUID projectId, final AddAgentToProjectRequest request) {
        return this.agentProjectClient.addAgentToProject(projectId, request);
    }
}
