package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectFlow;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import java.util.UUID;

/**
 * Port for automation agent project operations.
 */
public interface AgentProjectClient {

    AgentProject createAgentProject(CreateAgentProjectRequest request);

    AgentProjectsPageResponse getAgentProjects(Integer page, Integer size);

    AgentProject getAgentProject(UUID projectId);

    AgentProject patchAgentProject(UUID projectId, PatchAgentProjectRequest request);

    void deleteAgentProject(UUID projectId);

    ProjectAgentsResponse getProjectAgents(UUID projectId);

    ProjectAgent addAgentToProject(UUID projectId, AddAgentToProjectRequest request);

    void removeAgentFromProject(UUID projectId, UUID agentId);

    AgentProjectFlow getAgentProjectFlow(UUID projectId);

    AgentProjectFlowPaletteResponse getAgentProjectFlowPalette(UUID projectId);
}
