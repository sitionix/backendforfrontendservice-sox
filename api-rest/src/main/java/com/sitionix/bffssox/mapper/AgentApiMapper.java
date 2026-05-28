package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowEdgeDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowNodeDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowNodePositionDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowPaletteResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowPaletteSourceDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AddAgentToProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectFlow;
import com.sitionix.bffssox.domain.AgentProjectFlowEdge;
import com.sitionix.bffssox.domain.AgentProjectFlowNode;
import com.sitionix.bffssox.domain.AgentProjectFlowNodePosition;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteSource;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentApiMapper {

    AgentDTO asAgentDto(Agent src);

    AgentProjectDTO asAgentProjectDto(AgentProject src);

    AgentsResponseDTO asAgentsResponseDto(AgentsResponse src);

    AgentProjectsPageResponseDTO asAgentProjectsPageResponseDto(AgentProjectsPageResponse src);

    AddAgentToProjectRequest asAddAgentToProjectRequest(AddAgentToProjectRequestDTO src);

    ProjectAgentResponseDTO asProjectAgentResponseDto(ProjectAgent src);

    ProjectAgentsResponseDTO asProjectAgentsResponseDto(ProjectAgentsResponse src);

    AgentProjectFlowResponseDTO asAgentProjectFlowResponseDto(AgentProjectFlow src);

    AgentProjectFlowNodeDTO asAgentProjectFlowNodeDto(AgentProjectFlowNode src);

    AgentProjectFlowNodePositionDTO asAgentProjectFlowNodePositionDto(AgentProjectFlowNodePosition src);

    AgentProjectFlowEdgeDTO asAgentProjectFlowEdgeDto(AgentProjectFlowEdge src);

    AgentProjectFlowPaletteResponseDTO asAgentProjectFlowPaletteResponseDto(AgentProjectFlowPaletteResponse src);

    AgentProjectFlowPaletteSourceDTO asAgentProjectFlowPaletteSourceDto(AgentProjectFlowPaletteSource src);
}
