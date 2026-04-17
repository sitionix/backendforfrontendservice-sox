package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface PatchAgentClientMapper {

    PatchAgentRequestDTO asPatchAgentRequestDto(PatchAgentRequest src);

    default JsonNullable<String> map(final String value) {
        return value == null ? JsonNullable.undefined() : JsonNullable.of(value);
    }
}
