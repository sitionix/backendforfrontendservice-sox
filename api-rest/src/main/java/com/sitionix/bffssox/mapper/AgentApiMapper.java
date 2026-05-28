package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowPaletteResponseDTO1;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowPaletteSourceDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectFlowResponseDTO1;
import com.app_afesox.bffssox.api_first.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AddAgentToProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.GetAgentProjectFlowPaletteResponseDTO;
import com.app_afesox.bffssox.api_first.dto.GetAgentProjectFlowResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectFlowEdge;
import com.sitionix.bffssox.domain.AgentProjectFlowNode;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteSource;
import com.sitionix.bffssox.domain.AgentProjectFlowResponse;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentApiMapper {

    AgentDTO asAgentDto(Agent src);

    AgentProjectDTO asAgentProjectDto(AgentProject src);
    default GetAgentProjectFlowResponseDTO asGetAgentProjectFlowResponseDto(final AgentProjectFlowResponse src) {
        return src == null ? null : GetAgentProjectFlowResponseDTO.builder()
                .flow(this.asAgentProjectFlowResponseDTO1(src))
                .build();
    }

    default AgentProjectFlowResponseDTO1 asAgentProjectFlowResponseDTO1(final AgentProjectFlowResponse src) {
        return src == null ? null : AgentProjectFlowResponseDTO1.builder()
                .projectId(src.getProjectId())
                .flowId(src.getFlowId())
                .nodes(this.asAgentProjectFlowNodeDTO1s(src.getNodes()))
                .edges(this.asAgentProjectFlowEdgeDTOs(src.getEdges()))
                .build();
    }

    default List<com.app_afesox.bffssox.api_first.dto.AgentProjectFlowNodeDTO1> asAgentProjectFlowNodeDTO1s(final List<AgentProjectFlowNode> src) {
        if (src == null) {
            return null;
        }
        return src.stream().map(this::asAgentProjectFlowNodeDTO1).toList();
    }

    com.app_afesox.bffssox.api_first.dto.AgentProjectFlowNodeDTO1 asAgentProjectFlowNodeDTO1(AgentProjectFlowNode src);

    default List<com.app_afesox.bffssox.api_first.dto.AgentProjectFlowEdgeDTO> asAgentProjectFlowEdgeDTOs(final List<AgentProjectFlowEdge> src) {
        if (src == null) {
            return null;
        }
        return src.stream().map(this::asAgentProjectFlowEdgeDTO).toList();
    }

    com.app_afesox.bffssox.api_first.dto.AgentProjectFlowEdgeDTO asAgentProjectFlowEdgeDTO(AgentProjectFlowEdge src);

    default GetAgentProjectFlowPaletteResponseDTO asGetAgentProjectFlowPaletteResponseDto(final AgentProjectFlowPaletteResponse src) {
        return src == null ? null : GetAgentProjectFlowPaletteResponseDTO.builder()
                .palette(this.asAgentProjectFlowPaletteResponseDTO1(src))
                .build();
    }

    default AgentProjectFlowPaletteResponseDTO1 asAgentProjectFlowPaletteResponseDTO1(final AgentProjectFlowPaletteResponse src) {
        return src == null ? null : AgentProjectFlowPaletteResponseDTO1.builder()
                .sources(this.asAgentProjectFlowPaletteSourceDTOs(src.getSources()))
                .build();
    }

    default List<AgentProjectFlowPaletteSourceDTO> asAgentProjectFlowPaletteSourceDTOs(final List<AgentProjectFlowPaletteSource> src) {
        if (src == null) {
            return Collections.emptyList();
        }
        return src.stream().map(this::asAgentProjectFlowPaletteSourceDTO).toList();
    }

    AgentProjectFlowPaletteSourceDTO asAgentProjectFlowPaletteSourceDTO(AgentProjectFlowPaletteSource src);

    default AgentProjectFlowPaletteSourceDTO.SourceTypeEnum asSourceTypeEnum(final String sourceType) {
        return sourceType == null ? null : AgentProjectFlowPaletteSourceDTO.SourceTypeEnum.fromValue(sourceType);
    }

    AgentsResponseDTO asAgentsResponseDto(AgentsResponse src);

    AgentProjectsPageResponseDTO asAgentProjectsPageResponseDto(AgentProjectsPageResponse src);

    AddAgentToProjectRequest asAddAgentToProjectRequest(AddAgentToProjectRequestDTO src);

    ProjectAgentResponseDTO asProjectAgentResponseDto(ProjectAgent src);

    ProjectAgentsResponseDTO asProjectAgentsResponseDto(ProjectAgentsResponse src);
}
