package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface PatchAgentApiMapper {

    PatchAgentRequest asPatchAgentRequest(PatchAgentRequestDTO src);

    default String map(final JsonNullable<String> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }
}
