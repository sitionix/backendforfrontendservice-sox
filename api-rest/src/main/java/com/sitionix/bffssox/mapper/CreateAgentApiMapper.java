package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface CreateAgentApiMapper {

    CreateAgentRequest asCreateAgentRequest(CreateAgentRequestDTO src);

    default String map(final JsonNullable<String> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }
}
