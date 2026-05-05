package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.CreateAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateAgentProjectApiMapper {

    CreateAgentProjectRequest asCreateAgentProjectRequest(CreateAgentProjectRequestDTO src);
}
