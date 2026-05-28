package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentConversationDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationMessageDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ExecutionStatusDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitConversationExecutionRequestDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitConversationExecutionResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateProjectConversationRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationParticipantDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationProjectDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.SubmitConversationExecutionResponse;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversation;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import com.sitionix.bffssox.domain.ProjectConversationParticipant;
import com.sitionix.bffssox.domain.ProjectConversationProject;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
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

    @Mapping(target = "conversationId", ignore = true)
    ChatAgentRequest asChatAgentRequest(SubmitConversationExecutionRequestDTO src);

    @Mapping(target = "assistantMessage", source = "reply")
    ChatAgentResponseDTO asChatAgentResponseDto(ChatAgentResponse src);

    @Mapping(target = "status", source = "state")
    @Mapping(target = "acceptedAt", source = "createdAt")
    @Mapping(target = "inputMessageId", source = "inputMessageId")
    @Mapping(target = "error", ignore = true)
    SubmitChatExecutionResponseDTO asSubmitChatExecutionResponseDto(SubmitChatExecutionResponse src);

    @Mapping(target = "executionId", expression = "java(Boolean.TRUE.equals(src.getRuntimeDispatched()) ? src.getExecutionId() : null)")
    @Mapping(target = "executionStatus",
            expression = "java(mapSubmitExecutionStatus(src.getExecutionStatus(), src.getRuntimeDispatched()))")
    SubmitConversationExecutionResponseDTO asSubmitConversationExecutionResponseDto(SubmitConversationExecutionResponse src);

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
    @Mapping(target = "execution", source = "execution")
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

    CreateProjectConversationRequest asCreateProjectConversationRequest(CreateProjectConversationRequestDTO src);

    ProjectConversationDTO asProjectConversationDto(ProjectConversation src);

    ProjectConversationParticipantDTO asProjectConversationParticipantDto(ProjectConversationParticipant src);

    ProjectConversationProjectDTO asProjectConversationProjectDto(ProjectConversationProject src);

    @Mapping(target = "messages", source = "messages")
    ProjectConversationDetailsDTO asProjectConversationDetailsDto(ProjectConversationDetails src);

    @Mapping(target = "items", source = "items")
    ProjectConversationsResponseDTO asProjectConversationsResponseDto(ProjectConversationsResponse src);

    default ProjectConversationDTO.TypeEnum mapProjectConversationType(final String value) {
        return value == null ? null : ProjectConversationDTO.TypeEnum.fromValue(value);
    }

    default ProjectConversationDetailsDTO.TypeEnum mapProjectConversationDetailsType(final String value) {
        return value == null ? null : ProjectConversationDetailsDTO.TypeEnum.fromValue(value);
    }

    default ProjectConversationDTO.StatusEnum mapProjectConversationStatus(final String value) {
        return value == null ? null : ProjectConversationDTO.StatusEnum.fromValue(value);
    }

    default ProjectConversationDetailsDTO.StatusEnum mapProjectConversationDetailsStatus(final String value) {
        return value == null ? null : ProjectConversationDetailsDTO.StatusEnum.fromValue(value);
    }

    default ProjectConversationParticipantDTO.TypeEnum mapProjectConversationParticipantType(final String value) {
        return value == null ? null : ProjectConversationParticipantDTO.TypeEnum.fromValue(value);
    }

    default ProjectConversationParticipantDTO.StatusEnum mapProjectConversationParticipantStatus(final String value) {
        return value == null ? null : ProjectConversationParticipantDTO.StatusEnum.fromValue(value);
    }

    default ExecutionStatusDTO mapSubmitExecutionStatus(final String value, final Boolean runtimeDispatched) {
        if (!Boolean.TRUE.equals(runtimeDispatched)) {
            return null;
        }
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "QUEUED" -> ExecutionStatusDTO.ACCEPTED;
            case "COMPLETED" -> ExecutionStatusDTO.SUCCEEDED;
            default -> ExecutionStatusDTO.fromValue(value);
        };
    }

}
