package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectOperationsPort;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchAgentProjectImpl implements PatchAgentProject {

    private final AgentProjectOperationsPort agentClient;

    @Override
    public AgentProject execute(final UUID projectId, final PatchAgentProjectRequest request) {
        return this.agentClient.patchAgentProject(projectId, request);
    }
}
