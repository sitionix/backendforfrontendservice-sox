package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface CreateAgentClientMapper {

    CreateAgentRequestDTO asCreateAgentRequestDto(CreateAgentRequest src);

    default JsonNullable<String> map(final String value) {
        return JsonNullable.of(value);
    }
}
