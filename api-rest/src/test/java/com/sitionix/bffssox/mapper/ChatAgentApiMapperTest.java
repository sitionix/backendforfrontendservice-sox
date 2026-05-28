package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentConversationMessageDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDTO1;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionFailureDTO;
import com.app_afesox.bffssox.api_first.dto.CreateProjectConversationRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ExecutionStatusDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationParticipantDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationProjectDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitConversationExecutionResponseDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversation;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import com.sitionix.bffssox.domain.ProjectConversationParticipant;
import com.sitionix.bffssox.domain.ProjectConversationProject;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import com.sitionix.bffssox.domain.SubmitConversationExecutionResponse;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ChatAgentApiMapperTest {

    private ChatAgentApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ChatAgentApiMapperImpl(
                new ChatExecutionStatusApiMapperImpl(),
                new ChatExecutionFailureApiMapperImpl()
        );
    }

    @Test
    void givenChatAgentRequestDto_whenAsChatAgentRequest_thenReturnChatAgentRequest() {
        //given
        final UUID clientRequestId = UUID.fromString("742261a5-e89f-4153-96f3-f1275d2dd4e4");
        final ChatAgentRequestDTO given = this.getChatAgentRequestDto(clientRequestId, "Explain clean architecture");
        final ChatAgentRequest expected = this.getChatAgentRequest(clientRequestId, "Explain clean architecture");

        //when
        final ChatAgentRequest actual = this.mapper.asChatAgentRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatAgentResponse_whenAsChatAgentResponseDto_thenReturnChatAgentResponseDto() {
        //given
        final UUID conversationId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-21T10:01:00Z");
        final ChatAgentResponse given = this.getChatAgentResponse(
                conversationId,
                this.getChatAgentMessage(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        "AGENT",
                        "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0",
                        "Clean architecture separates business rules from frameworks.",
                        createdAt
                )
        );
        final ChatAgentResponseDTO expected = this.getChatAgentResponseDto(
                conversationId,
                this.getAgentConversationMessageDto(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        AgentConversationMessageDTO.AuthorTypeEnum.AGENT,
                        "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0",
                        "Clean architecture separates business rules from frameworks.",
                        createdAt
                )
        );

        //when
        final ChatAgentResponseDTO actual = this.mapper.asChatAgentResponseDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullChatAgentRequestDto_whenAsChatAgentRequest_thenReturnNull() {
        //given
        final ChatAgentRequestDTO given = null;

        //when
        final ChatAgentRequest actual = this.mapper.asChatAgentRequest(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenNullChatAgentResponse_whenAsChatAgentResponseDto_thenReturnNull() {
        //given
        final ChatAgentResponse given = null;

        //when
        final ChatAgentResponseDTO actual = this.mapper.asChatAgentResponseDto(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenAgentConversationsResponse_whenAsAgentConversationsResponseDto_thenReturnMappedItems() {
        //given
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-21T10:00:00Z");
        final OffsetDateTime updatedAt = OffsetDateTime.parse("2026-04-21T10:01:00Z");
        final AgentConversationsResponse given = this.getAgentConversationsResponse(List.of(
                this.getAgentConversation(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "Explain clean architecture",
                        "DIRECT",
                        createdAt,
                        updatedAt
                )
        ));
        final AgentConversationsResponseDTO expected = this.getAgentConversationsResponseDto(List.of(
                this.getAgentConversationDto(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "Explain clean architecture",
                        AgentConversationDTO1.TypeEnum.DIRECT,
                        createdAt,
                        updatedAt
                )
        ));

        //when
        final AgentConversationsResponseDTO actual = this.mapper.asAgentConversationsResponseDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentConversationDetails_whenAsAgentConversationDetailsDto_thenReturnMappedDetails() {
        //given
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-21T10:01:00Z");
        final AgentConversationDetails given = this.getAgentConversationDetails(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Explain clean architecture",
                "DIRECT",
                OffsetDateTime.parse("2026-04-21T10:00:00Z"),
                createdAt,
                List.of(this.getChatAgentMessage(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        "AGENT",
                        "agent-1",
                        "Clean architecture separates business logic.",
                        createdAt
                )),
                List.of(this.getFailedChatExecution())
        );
        final AgentConversationDetailsDTO expected = this.getAgentConversationDetailsDto(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Explain clean architecture",
                AgentConversationDetailsDTO.TypeEnum.DIRECT,
                OffsetDateTime.parse("2026-04-21T10:00:00Z"),
                createdAt,
                List.of(this.getAgentConversationMessageDto(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        AgentConversationMessageDTO.AuthorTypeEnum.AGENT,
                        "agent-1",
                        "Clean architecture separates business logic.",
                        createdAt
                )),
                this.mapper.asChatExecutionDto(this.getFailedChatExecution())
        );

        //when
        final AgentConversationDetailsDTO actual = this.mapper.asAgentConversationDetailsDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSubmitChatExecutionResponse_whenAsSubmitChatExecutionResponseDto_thenReturnMappedDto() {
        //given
        final SubmitChatExecutionResponse given = this.getSubmitChatExecutionResponse();

        //when
        final SubmitChatExecutionResponseDTO actual = this.mapper.asSubmitChatExecutionResponseDto(given);

        //then
        assertThat(actual.getExecutionId()).isEqualTo(given.getExecutionId());
        assertThat(actual.getConversationId()).isEqualTo(given.getConversationId());
        assertThat(actual.getInputMessageId()).isEqualTo(given.getInputMessageId());
        assertThat(actual.getStatus()).isEqualTo(ExecutionStatusDTO.ACCEPTED);
        assertThat(actual.getAcceptedAt()).isEqualTo(given.getCreatedAt());
    }

    @Test
    void givenChatExecution_whenAsChatExecutionDto_thenReturnMappedDto() {
        //given
        final ChatExecution given = this.getFailedChatExecution();

        //when
        final ChatExecutionDTO actual = this.mapper.asChatExecutionDto(given);

        //then
        assertThat(actual.getStatus()).isEqualTo(ExecutionStatusDTO.FAILED);
        assertThat(actual.getError()).isEqualTo(ChatExecutionFailureDTO.builder()
                .code("EXECUTION_ERROR")
                .message("Execution failed")
                .details(java.util.Map.of("retryable", true))
                .build());
    }

    @Test
    void givenCreateProjectConversationRequestDto_whenAsCreateProjectConversationRequest_thenReturnMappedDomain() {
        //given
        final UUID agentId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        final CreateProjectConversationRequestDTO given = CreateProjectConversationRequestDTO.builder()
                .agentIds(Set.of(agentId))
                .build();

        //when
        final CreateProjectConversationRequest actual = this.mapper.asCreateProjectConversationRequest(given);

        //then
        assertThat(actual.getAgentIds()).isEqualTo(List.of(agentId));
    }

    @Test
    void givenProjectConversationsResponse_whenAsProjectConversationsResponseDto_thenReturnMappedDto() {
        //given
        final ProjectConversationsResponse given = ProjectConversationsResponse.builder()
                .items(List.of(this.getProjectConversation()))
                .build();

        //when
        final ProjectConversationsResponseDTO actual = this.mapper.asProjectConversationsResponseDto(given);

        //then
        assertThat(actual.getItems()).hasSize(1);
        assertThat(actual.getItems().get(0).getType()).isEqualTo(ProjectConversationDTO.TypeEnum.MULTI_AGENT);
        assertThat(actual.getItems().get(0).getStatus()).isEqualTo(ProjectConversationDTO.StatusEnum.ACTIVE);
    }

    @Test
    void givenProjectConversationDetails_whenAsProjectConversationDetailsDto_thenReturnMappedDto() {
        //given
        final ProjectConversationDetails given = this.getProjectConversationDetails();

        //when
        final ProjectConversationDetailsDTO actual = this.mapper.asProjectConversationDetailsDto(given);

        //then
        assertThat(actual.getType()).isEqualTo(ProjectConversationDetailsDTO.TypeEnum.MULTI_AGENT);
        assertThat(actual.getStatus()).isEqualTo(ProjectConversationDetailsDTO.StatusEnum.ACTIVE);
        assertThat(actual.getProject().getName()).isEqualTo("Sitionix");
        assertThat(actual.getParticipants()).hasSize(1);
    }

    @Test
    void givenSubmitConversationExecutionResponseWithRuntimeDispatch_whenAsSubmitConversationExecutionResponseDto_thenReturnExecutionMetadata() {
        //given
        final SubmitConversationExecutionResponse given = this.getSubmitConversationExecutionResponse(Boolean.TRUE, "QUEUED");

        //when
        final SubmitConversationExecutionResponseDTO actual = this.mapper.asSubmitConversationExecutionResponseDto(given);

        //then
        assertThat(actual.getConversationId()).isEqualTo(given.getConversationId());
        assertThat(actual.getInputMessageId()).isEqualTo(given.getInputMessageId());
        assertThat(actual.getExecutionId()).isEqualTo(given.getExecutionId());
        assertThat(actual.getExecutionStatus()).isEqualTo(ExecutionStatusDTO.ACCEPTED);
    }

    @Test
    void givenSubmitConversationExecutionResponseWithoutRuntimeDispatch_whenAsSubmitConversationExecutionResponseDto_thenReturnNullExecutionMetadata() {
        //given
        final SubmitConversationExecutionResponse given = this.getSubmitConversationExecutionResponse(Boolean.FALSE, "QUEUED");

        //when
        final SubmitConversationExecutionResponseDTO actual = this.mapper.asSubmitConversationExecutionResponseDto(given);

        //then
        assertThat(actual.getConversationId()).isEqualTo(given.getConversationId());
        assertThat(actual.getInputMessageId()).isEqualTo(given.getInputMessageId());
        assertThat(actual.getExecutionId()).isNull();
        assertThat(actual.getExecutionStatus()).isNull();
    }

    @Test
    void givenRuntimeDispatchedNull_whenMapSubmitExecutionStatus_thenReturnNull() {
        //given
        final String executionStatus = "QUEUED";
        final Boolean runtimeDispatched = null;

        //when
        final ExecutionStatusDTO actual = this.mapper.mapSubmitExecutionStatus(executionStatus, runtimeDispatched);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenCompletedExecutionStatusAndRuntimeDispatchedTrue_whenMapSubmitExecutionStatus_thenReturnSucceeded() {
        //given
        final String executionStatus = "COMPLETED";
        final Boolean runtimeDispatched = Boolean.TRUE;

        //when
        final ExecutionStatusDTO actual = this.mapper.mapSubmitExecutionStatus(executionStatus, runtimeDispatched);

        //then
        assertThat(actual).isEqualTo(ExecutionStatusDTO.SUCCEEDED);
    }

    @Test
    void givenNullExecutions_whenMapExecution_thenReturnNull() {
        //given
        final List<ChatExecution> executions = null;

        //when
        final ChatExecutionDTO actual = this.mapper.mapExecution(executions);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenEmptyExecutions_whenMapExecution_thenReturnNull() {
        //given
        final List<ChatExecution> executions = Collections.emptyList();

        //when
        final ChatExecutionDTO actual = this.mapper.mapExecution(executions);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenNullProjectConversationEnums_whenMapProjectConversationEnums_thenReturnNulls() {
        //given
        final String nullValue = null;

        //when
        final ProjectConversationDTO.TypeEnum actualType = this.mapper.mapProjectConversationType(nullValue);
        final ProjectConversationDetailsDTO.TypeEnum actualDetailsType = this.mapper.mapProjectConversationDetailsType(nullValue);
        final ProjectConversationDTO.StatusEnum actualStatus = this.mapper.mapProjectConversationStatus(nullValue);
        final ProjectConversationDetailsDTO.StatusEnum actualDetailsStatus = this.mapper.mapProjectConversationDetailsStatus(nullValue);
        final ProjectConversationParticipantDTO.TypeEnum actualParticipantType =
                this.mapper.mapProjectConversationParticipantType(nullValue);
        final ProjectConversationParticipantDTO.StatusEnum actualParticipantStatus =
                this.mapper.mapProjectConversationParticipantStatus(nullValue);

        //then
        assertThat(actualType).isNull();
        assertThat(actualDetailsType).isNull();
        assertThat(actualStatus).isNull();
        assertThat(actualDetailsStatus).isNull();
        assertThat(actualParticipantType).isNull();
        assertThat(actualParticipantStatus).isNull();
    }

    @Test
    void givenNullAgentConversationEnums_whenMapEnums_thenReturnNulls() {
        //given
        final String nullValue = null;

        //when
        final AgentConversationDetailsDTO.TypeEnum actualDetailsType = this.mapper.map(nullValue);
        final AgentConversationDTO.TypeEnum actualConversationType = this.mapper.mapConversationType(nullValue);
        final AgentConversationMessageDTO.AuthorTypeEnum actualAuthorType = this.mapper.mapAuthorType(nullValue);

        //then
        assertThat(actualDetailsType).isNull();
        assertThat(actualConversationType).isNull();
        assertThat(actualAuthorType).isNull();
    }

    private ChatAgentRequestDTO getChatAgentRequestDto(final UUID clientRequestId, final String message) {
        return ChatAgentRequestDTO.builder()
                .clientRequestId(clientRequestId)
                .message(message)
                .build();
    }

    private ProjectConversation getProjectConversation() {
        return ProjectConversation.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .projectId(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .type("MULTI_AGENT")
                .title("Team chat")
                .status("ACTIVE")
                .participants(List.of(this.getProjectConversationParticipant()))
                .canSendMessages(Boolean.FALSE)
                .createdAt(OffsetDateTime.parse("2026-05-08T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-05-08T10:01:00Z"))
                .lastMessageAt(null)
                .build();
    }

    private ProjectConversationDetails getProjectConversationDetails() {
        return ProjectConversationDetails.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .projectId(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .project(ProjectConversationProject.builder()
                        .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                        .name("Sitionix")
                        .context("Project context")
                        .build())
                .type("MULTI_AGENT")
                .title("Team chat")
                .status("ACTIVE")
                .participants(List.of(this.getProjectConversationParticipant()))
                .messages(List.of())
                .canSendMessages(Boolean.FALSE)
                .createdAt(OffsetDateTime.parse("2026-05-08T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-05-08T10:01:00Z"))
                .lastMessageAt(null)
                .build();
    }

    private ProjectConversationParticipant getProjectConversationParticipant() {
        return ProjectConversationParticipant.builder()
                .type("AGENT")
                .agentId(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                .name("Writer")
                .description("Writes copy")
                .status("ACTIVE")
                .build();
    }

    private ChatAgentRequest getChatAgentRequest(final UUID clientRequestId, final String message) {
        return ChatAgentRequest.builder()
                .clientRequestId(clientRequestId)
                .message(message)
                .build();
    }

    private ChatAgentResponse getChatAgentResponse(final UUID conversationId, final ChatAgentMessage reply) {
        return ChatAgentResponse.builder()
                .conversationId(conversationId)
                .reply(reply)
                .build();
    }

    private ChatAgentResponseDTO getChatAgentResponseDto(final UUID conversationId, final AgentConversationMessageDTO reply) {
        return ChatAgentResponseDTO.builder()
                .conversationId(conversationId)
                .assistantMessage(reply)
                .build();
    }

    private AgentConversationsResponse getAgentConversationsResponse(final List<AgentConversation> items) {
        return AgentConversationsResponse.builder()
                .items(items)
                .build();
    }

    private AgentConversationsResponseDTO getAgentConversationsResponseDto(final List<AgentConversationDTO1> items) {
        return AgentConversationsResponseDTO.builder()
                .items(items)
                .build();
    }

    private AgentConversation getAgentConversation(
            final UUID id,
            final String title,
            final String type,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        return AgentConversation.builder()
                .id(id)
                .title(title)
                .type(type)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastMessageAt(updatedAt)
                .build();
    }

    private AgentConversationDTO1 getAgentConversationDto(
            final UUID id,
            final String title,
            final AgentConversationDTO1.TypeEnum type,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        return AgentConversationDTO1.builder()
                .id(id)
                .title(title)
                .type(type)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastMessageAt(updatedAt)
                .build();
    }

    private AgentConversationDetails getAgentConversationDetails(
            final UUID id,
            final String title,
            final String type,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final List<ChatAgentMessage> messages,
            final List<ChatExecution> executions
    ) {
        return AgentConversationDetails.builder()
                .id(id)
                .title(title)
                .type(type)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastMessageAt(updatedAt)
                .messages(messages)
                .executions(executions)
                .build();
    }

    private AgentConversationDetailsDTO getAgentConversationDetailsDto(
            final UUID id,
            final String title,
            final AgentConversationDetailsDTO.TypeEnum type,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final List<AgentConversationMessageDTO> messages,
            final ChatExecutionDTO execution
    ) {
        return AgentConversationDetailsDTO.builder()
                .id(id)
                .title(title)
                .type(type)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastMessageAt(updatedAt)
                .messages(messages)
                .execution(execution)
                .build();
    }

    private ChatAgentMessage getChatAgentMessage(
            final UUID id,
            final String authorType,
            final String authorId,
            final String content,
            final OffsetDateTime createdAt
    ) {
        return ChatAgentMessage.builder()
                .id(id)
                .authorType(authorType)
                .authorId(authorId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

    private AgentConversationMessageDTO getAgentConversationMessageDto(
            final UUID id,
            final AgentConversationMessageDTO.AuthorTypeEnum authorType,
            final String authorId,
            final String content,
            final OffsetDateTime createdAt
    ) {
        return AgentConversationMessageDTO.builder()
                .id(id)
                .authorType(authorType)
                .authorId(authorId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

    private SubmitChatExecutionResponse getSubmitChatExecutionResponse() {
        return SubmitChatExecutionResponse.builder()
                .executionId(UUID.fromString("d8827667-03f3-4d46-ae0d-d35e43ecdf95"))
                .conversationId(UUID.fromString("5bddb194-5ca2-4461-9b6b-c5f986fa86ea"))
                .inputMessageId(UUID.fromString("f0beec7e-5c98-48b9-ae82-0a6952576a7a"))
                .state("QUEUED")
                .createdAt(OffsetDateTime.parse("2026-04-29T10:00:00Z"))
                .idempotencyKey("idem")
                .idempotencyReplayed(false)
                .build();
    }

    private SubmitConversationExecutionResponse getSubmitConversationExecutionResponse(
            final Boolean runtimeDispatched,
            final String executionStatus
    ) {
        return SubmitConversationExecutionResponse.builder()
                .executionId(UUID.fromString("d8827667-03f3-4d46-ae0d-d35e43ecdf95"))
                .conversationId(UUID.fromString("5bddb194-5ca2-4461-9b6b-c5f986fa86ea"))
                .inputMessageId(UUID.fromString("f0beec7e-5c98-48b9-ae82-0a6952576a7a"))
                .runtimeDispatched(runtimeDispatched)
                .executionStatus(executionStatus)
                .build();
    }

    private ChatExecution getFailedChatExecution() {
        return ChatExecution.builder()
                .executionId(UUID.fromString("d8827667-03f3-4d46-ae0d-d35e43ecdf95"))
                .conversationId(UUID.fromString("5bddb194-5ca2-4461-9b6b-c5f986fa86ea"))
                .agentId(UUID.fromString("6e4e32f8-2f48-4600-9a73-bb026f98dbf4"))
                .state("FAILED")
                .createdAt(OffsetDateTime.parse("2026-04-29T10:00:00Z"))
                .failure(this.getChatExecutionFailure())
                .build();
    }

    private ChatExecutionFailure getChatExecutionFailure() {
        return ChatExecutionFailure.builder()
                .failureClass("EXECUTION_ERROR")
                .reason("Execution failed")
                .retryable(true)
                .build();
    }
}
