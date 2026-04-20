package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatAgentApiMapper {

    ChatAgentRequest asChatAgentRequest(ChatAgentRequestDTO src);

    ChatAgentResponseDTO asChatAgentResponseDto(ChatAgentResponse src);
}
