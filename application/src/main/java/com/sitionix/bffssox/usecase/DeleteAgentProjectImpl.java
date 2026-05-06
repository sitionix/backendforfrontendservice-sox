package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectOperationsPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAgentProjectImpl implements DeleteAgentProject {

    private final AgentProjectOperationsPort agentClient;

    @Override
    public void execute(final UUID projectId) {
        this.agentClient.deleteAgentProject(projectId);
    }
}
