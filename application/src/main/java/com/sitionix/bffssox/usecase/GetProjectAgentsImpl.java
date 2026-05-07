package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectClient;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectAgentsImpl implements GetProjectAgents {

    private final AgentProjectClient agentProjectClient;

    @Override
    public ProjectAgentsResponse execute(final UUID projectId) {
        return this.agentProjectClient.getProjectAgents(projectId);
    }
}
