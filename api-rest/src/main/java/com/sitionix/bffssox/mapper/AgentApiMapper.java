package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface AgentApiMapper {

    AgentDTO asAgentDto(Agent src);

    AgentsResponseDTO asAgentsResponseDto(AgentsResponse src);

    default String map(final JsonNullable<String> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }

    default JsonNullable<String> map(final String value) {
        return JsonNullable.of(value);
    }
}
