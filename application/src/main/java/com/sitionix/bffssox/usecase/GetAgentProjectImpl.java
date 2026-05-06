package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentProject;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentProjectImpl implements GetAgentProject {

    private final AgentClient agentClient;

    @Override
    public AgentProject execute(final UUID projectId) {
        return this.agentClient.getAgentProject(projectId);
    }
}
