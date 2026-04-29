package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentConversationDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationMessageDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionFailureDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ExecutionStatusDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatAgentApiMapper {

    ChatAgentRequest asChatAgentRequest(ChatAgentRequestDTO src);

    @Mapping(target = "assistantMessage", source = "reply")
    ChatAgentResponseDTO asChatAgentResponseDto(ChatAgentResponse src);

    @Mapping(target = "status", source = "state")
    @Mapping(target = "acceptedAt", source = "createdAt")
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

    default ExecutionStatusDTO mapExecutionStatus(final String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "QUEUED" -> ExecutionStatusDTO.ACCEPTED;
            case "COMPLETED" -> ExecutionStatusDTO.SUCCEEDED;
            default -> ExecutionStatusDTO.fromValue(value);
        };
    }

    default ChatExecutionFailureDTO asChatExecutionFailureDto(final ChatExecutionFailure src) {
        if (src == null) {
            return null;
        }
        return ChatExecutionFailureDTO.builder()
                .code(src.getFailureClass())
                .message(src.getReason())
                .details(src.getRetryable() == null ? null : java.util.Map.of("retryable", src.getRetryable()))
                .build();
    }
}
