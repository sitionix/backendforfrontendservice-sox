package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface AgentClientMapper {

    Agent asAgent(AgentDTO src);

    AgentsResponse asAgentsResponse(AgentsResponseDTO src);

    default String map(final JsonNullable<String> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }
}
