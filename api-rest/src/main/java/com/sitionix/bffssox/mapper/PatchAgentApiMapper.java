package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatchAgentApiMapper {

    PatchAgentRequest asPatchAgentRequest(PatchAgentRequestDTO src);
}
