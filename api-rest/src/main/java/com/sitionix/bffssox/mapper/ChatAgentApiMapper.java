package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentConversationDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationMessageDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        ChatExecutionStatusApiMapper.class,
        ChatExecutionFailureApiMapper.class
})
public interface ChatAgentApiMapper {

    @Mapping(target = "clientRequestId", source = "clientRequestId")
    ChatAgentRequest asChatAgentRequest(ChatAgentRequestDTO src);

    @Mapping(target = "assistantMessage", source = "reply")
    ChatAgentResponseDTO asChatAgentResponseDto(ChatAgentResponse src);

    @Mapping(target = "status", source = "state")
    @Mapping(target = "acceptedAt", source = "createdAt")
    @Mapping(target = "inputMessageId", source = "inputMessageId")
    @Mapping(target = "error", ignore = true)
    SubmitChatExecutionResponseDTO asSubmitChatExecutionResponseDto(SubmitChatExecutionResponse src);

    @Mapping(target = "status", source = "state")
    @Mapping(target = "acceptedAt", source = "createdAt")
    @Mapping(target = "error", source = "failure")
    ChatExecutionDTO asChatExecutionDto(ChatExecution src);

    AgentConversationDTO asAgentConversationDto(AgentConversation src);

    AgentConversationMessageDTO asAgentConversationMessageDto(ChatAgentMessage src);

    List<AgentConversationDTO> asAgentConversationDtos(List<AgentConversation> src);

    List<AgentConversationMessageDTO> asAgentConversationMessageDtos(List<ChatAgentMessage> src);

    @Mapping(target = "items", source = "items")
    AgentConversationsResponseDTO asAgentConversationsResponseDto(AgentConversationsResponse src);

    @Mapping(target = "messages", source = "messages")
    @Mapping(target = "executions", source = "executions")
    AgentConversationDetailsDTO asAgentConversationDetailsDto(AgentConversationDetails src);

    default AgentConversationDetailsDTO.TypeEnum map(final String value) {
        return value == null ? null : AgentConversationDetailsDTO.TypeEnum.fromValue(value);
    }

    default AgentConversationDTO.TypeEnum mapConversationType(final String value) {
        return value == null ? null : AgentConversationDTO.TypeEnum.fromValue(value);
    }

    default AgentConversationMessageDTO.AuthorTypeEnum mapAuthorType(final String value) {
        return value == null ? null : AgentConversationMessageDTO.AuthorTypeEnum.fromValue(value);
    }

}
