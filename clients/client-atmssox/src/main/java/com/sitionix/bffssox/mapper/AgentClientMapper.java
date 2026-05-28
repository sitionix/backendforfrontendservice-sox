package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectFlowPaletteResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectFlowResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.AddAgentToProjectRequestDTO;
import com.app_afesox.atmssox.client.dto.ProjectAgentResponseDTO;
import com.app_afesox.atmssox.client.dto.ProjectAgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import com.sitionix.bffssox.domain.AgentProjectFlowResponse;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentClientMapper {

    Agent asAgent(AgentDTO src);

    AgentProject asAgentProject(AgentProjectDTO src);
    AgentProjectFlowResponse asAgentProjectFlowResponse(AgentProjectFlowResponseDTO src);
    AgentProjectFlowPaletteResponse asAgentProjectFlowPaletteResponse(AgentProjectFlowPaletteResponseDTO src);

    AgentsResponse asAgentsResponse(AgentsResponseDTO src);

    AgentProjectsPageResponse asAgentProjectsPageResponse(AgentProjectsPageResponseDTO src);

    AddAgentToProjectRequestDTO asAddAgentToProjectRequestDto(AddAgentToProjectRequest src);

    ProjectAgent asProjectAgent(ProjectAgentResponseDTO src);

    ProjectAgentsResponse asProjectAgentsResponse(ProjectAgentsResponseDTO src);
}
