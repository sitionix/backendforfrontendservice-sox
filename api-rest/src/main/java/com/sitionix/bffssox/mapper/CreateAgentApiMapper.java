package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateAgentApiMapper {

    CreateAgentRequest asCreateAgentRequest(CreateAgentRequestDTO src);
}
