package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentConversationDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationMessageDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatAgentApiMapper {

    ChatAgentRequest asChatAgentRequest(ChatAgentRequestDTO src);

    ChatAgentResponseDTO asChatAgentResponseDto(ChatAgentResponse src);

    AgentConversationDTO asAgentConversationDto(AgentConversation src);

    AgentConversationMessageDTO asAgentConversationMessageDto(ChatAgentMessage src);

    List<AgentConversationDTO> asAgentConversationDtos(List<AgentConversation> src);

    List<AgentConversationMessageDTO> asAgentConversationMessageDtos(List<ChatAgentMessage> src);

    default AgentConversationsResponseDTO asAgentConversationsResponseDto(final AgentConversationsResponse src) {
        return AgentConversationsResponseDTO.builder()
                .items(this.asAgentConversationDtos(src.getItems()))
                .build();
    }

    default AgentConversationDetailsDTO asAgentConversationDetailsDto(final AgentConversationDetails src) {
        return AgentConversationDetailsDTO.builder()
                .id(src.getId())
                .title(src.getTitle())
                .type(AgentConversationDetailsDTO.TypeEnum.fromValue(src.getType()))
                .createdAt(src.getCreatedAt())
                .updatedAt(src.getUpdatedAt())
                .lastMessageAt(src.getLastMessageAt())
                .messages(this.asAgentConversationMessageDtos(src.getMessages()))
                .build();
    }
}
