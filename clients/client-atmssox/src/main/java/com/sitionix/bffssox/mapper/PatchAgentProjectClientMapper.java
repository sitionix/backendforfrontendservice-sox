package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.PatchAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatchAgentProjectClientMapper {

    PatchAgentProjectRequestDTO asPatchAgentProjectRequestDto(PatchAgentProjectRequest src);
}
