package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatchAgentClientMapper {

    PatchAgentRequestDTO asPatchAgentRequestDto(PatchAgentRequest src);
}
