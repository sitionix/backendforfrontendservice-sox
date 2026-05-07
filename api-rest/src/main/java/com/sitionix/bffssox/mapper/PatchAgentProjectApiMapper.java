package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.PatchAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatchAgentProjectApiMapper {

    PatchAgentProjectRequest asPatchAgentProjectRequest(PatchAgentProjectRequestDTO src);
}
