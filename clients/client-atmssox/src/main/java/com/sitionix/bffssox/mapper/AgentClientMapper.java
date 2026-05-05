package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentClientMapper {

    Agent asAgent(AgentDTO src);

    AgentProject asAgentProject(AgentProjectDTO src);

    AgentsResponse asAgentsResponse(AgentsResponseDTO src);

    AgentProjectsPageResponse asAgentProjectsPageResponse(AgentProjectsPageResponseDTO src);
}
