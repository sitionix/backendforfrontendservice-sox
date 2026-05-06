package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectOperationsPort;
import com.sitionix.bffssox.domain.AgentProject;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentProjectImpl implements GetAgentProject {

    private final AgentProjectOperationsPort agentClient;

    @Override
    public AgentProject execute(final UUID projectId) {
        return this.agentClient.getAgentProject(projectId);
    }
}
