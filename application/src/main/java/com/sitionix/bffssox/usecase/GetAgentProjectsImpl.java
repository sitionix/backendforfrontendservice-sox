package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentProjectsImpl implements GetAgentProjects {

    private final AgentClient agentClient;

    @Override
    public AgentProjectsPageResponse execute(final Integer page, final Integer size) {
        return this.agentClient.getAgentProjects(page, size);
    }
}
