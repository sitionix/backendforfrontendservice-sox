package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectOperationsPort;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAgentProjectImpl implements CreateAgentProject {

    private final AgentProjectOperationsPort agentClient;

    @Override
    public AgentProject execute(final CreateAgentProjectRequest request) {
        return this.agentClient.createAgentProject(request);
    }
}
