package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentConversationMessageDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDTO1;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentResponseDTO;
import com.app_afesox.atmssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.api_first.dto.ChatExecutionFailureDTO;
import com.app_afesox.atmssox.api_first.dto.ExecutionStatusDTO;
import com.app_afesox.atmssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ChatAgentClientMapperTest {

    private ChatAgentClientMapper mapper;
    private ChatExecutionStatusClientMapper chatExecutionStatusMapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ChatAgentClientMapperImpl(
                new ChatExecutionStatusClientMapperImpl(),
                new ChatExecutionFailureClientMapperImpl()
        );
        this.chatExecutionStatusMapper = new ChatExecutionStatusClientMapperImpl();
    }

    @Test
    void givenChatAgentRequest_whenAsChatAgentRequestDto_thenReturnChatAgentRequestDto() {
        //given
        final ChatAgentRequest given = this.getChatAgentRequest("Explain SOLID");
        final ChatAgentRequestDTO expected = this.getChatAgentRequestDto("Explain SOLID");

        //when
        final ChatAgentRequestDTO actual = this.mapper.asChatAgentRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatAgentResponseDto_whenAsChatAgentResponse_thenReturnChatAgentResponse() {
        //given
        final UUID conversationId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-21T10:01:00Z");
        final ChatAgentResponseDTO given = this.getChatAgentResponseDto(
                conversationId,
                this.getAgentConversationMessageDto(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        AgentConversationMessageDTO.AuthorTypeEnum.AGENT,
                        "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0",
                        "SOLID is a set of design principles.",
                        createdAt
                )
        );
        final ChatAgentResponse expected = this.getChatAgentResponse(
                conversationId,
                null
        );

        //when
        final ChatAgentResponse actual = this.mapper.asChatAgentResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
        assertThat(actual.getReply()).isNull();
    }

    @Test
    void givenNullChatAgentRequest_whenAsChatAgentRequestDto_thenReturnNull() {
        //given
        final ChatAgentRequest given = null;

        //when
        final ChatAgentRequestDTO actual = this.mapper.asChatAgentRequestDto(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenNullChatAgentResponseDto_whenAsChatAgentResponse_thenReturnNull() {
        //given
        final ChatAgentResponseDTO given = null;

        //when
        final ChatAgentResponse actual = this.mapper.asChatAgentResponse(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenAgentConversationsResponseDto_whenAsAgentConversationsResponse_thenReturnMappedItems() {
        //given
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-21T10:00:00Z");
        final OffsetDateTime updatedAt = OffsetDateTime.parse("2026-04-21T10:01:00Z");
        final AgentConversationsResponseDTO given = this.getAgentConversationsResponseDto(List.of(
                this.getAgentConversationDto(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "Explain clean architecture",
                        AgentConversationDTO1.TypeEnum.DIRECT,
                        createdAt,
                        updatedAt
                )
        ));
        final AgentConversationsResponse expected = this.getAgentConversationsResponse(List.of(
                this.getAgentConversation(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "Explain clean architecture",
                        "DIRECT",
                        createdAt,
                        updatedAt
                )
        ));

        //when
        final AgentConversationsResponse actual = this.mapper.asAgentConversationsResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentConversationDetailsDto_whenAsAgentConversationDetails_thenReturnMappedDetails() {
        //given
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-21T10:01:00Z");
        final AgentConversationDetailsDTO given = this.getAgentConversationDetailsDto(
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
                ))
        );
        final AgentConversationDetails expected = this.getAgentConversationDetails(
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
                ))
        );

        //when
        final AgentConversationDetails actual = this.mapper.asAgentConversationDetails(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSubmitChatExecutionResponseDto_whenAsSubmitChatExecutionResponse_thenReturnMappedDomain() {
        //given
        final SubmitChatExecutionResponseDTO given = this.getSubmitChatExecutionResponseDto();
        final SubmitChatExecutionResponse expected = this.getSubmitChatExecutionResponse();

        //when
        final SubmitChatExecutionResponse actual = this.mapper.asSubmitChatExecutionResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatExecutionDto_whenAsChatExecution_thenReturnMappedDomain() {
        //given
        final ChatExecutionDTO given = this.getFailedChatExecutionDto();

        //when
        final ChatExecution actual = this.mapper.asChatExecution(given);

        //then
        assertThat(actual.getState()).isEqualTo("FAILED");
        assertThat(actual.getFailure().getFailureClass()).isEqualTo("EXECUTION_ERROR");
        assertThat(actual.getFailure().getReason()).isEqualTo("Execution failed");
        assertThat(actual.getFailure().getRetryable()).isTrue();
    }

    @Test
    void givenAllLifecycleStatesFromAtms_whenAsChatExecution_thenReturnCanonicalExternalStates() {
        //given
        final List<ExecutionStatusDTO> given = List.of(
                ExecutionStatusDTO.ACCEPTED,
                ExecutionStatusDTO.IN_PROGRESS,
                ExecutionStatusDTO.SUCCEEDED,
                ExecutionStatusDTO.FAILED
        );

        //when
        final List<String> actual = given.stream()
                .map(this.chatExecutionStatusMapper::mapExecutionStatus)
                .toList();

        //then
        assertThat(actual).isEqualTo(List.of("QUEUED", "IN_PROGRESS", "COMPLETED", "FAILED"));
    }

    private ChatAgentRequest getChatAgentRequest(final String message) {
        return ChatAgentRequest.builder()
                .message(message)
                .build();
    }

    private ChatAgentRequestDTO getChatAgentRequestDto(final String message) {
        return ChatAgentRequestDTO.builder()
                .message(message)
                .build();
    }

    private ChatAgentResponseDTO getChatAgentResponseDto(final UUID conversationId, final AgentConversationMessageDTO reply) {
        return ChatAgentResponseDTO.builder()
                .conversationId(conversationId)
                .build();
    }

    private ChatAgentResponse getChatAgentResponse(final UUID conversationId, final ChatAgentMessage reply) {
        return ChatAgentResponse.builder()
                .conversationId(conversationId)
                .reply(reply)
                .build();
    }

    private AgentConversationsResponseDTO getAgentConversationsResponseDto(final List<AgentConversationDTO1> items) {
        return AgentConversationsResponseDTO.builder()
                .items(items)
                .build();
    }

    private AgentConversationsResponse getAgentConversationsResponse(final List<AgentConversation> items) {
        return AgentConversationsResponse.builder()
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

    private AgentConversationDetailsDTO getAgentConversationDetailsDto(
            final UUID id,
            final String title,
            final AgentConversationDetailsDTO.TypeEnum type,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final List<AgentConversationMessageDTO> messages
    ) {
        return AgentConversationDetailsDTO.builder()
                .id(id)
                .title(title)
                .type(type)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastMessageAt(updatedAt)
                .messages(messages)
                .build();
    }

    private AgentConversationDetails getAgentConversationDetails(
            final UUID id,
            final String title,
            final String type,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final List<ChatAgentMessage> messages
    ) {
        return AgentConversationDetails.builder()
                .id(id)
                .title(title)
                .type(type)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastMessageAt(updatedAt)
                .messages(messages)
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

    private SubmitChatExecutionResponseDTO getSubmitChatExecutionResponseDto() {
        return SubmitChatExecutionResponseDTO.builder()
                .executionId(UUID.fromString("d8827667-03f3-4d46-ae0d-d35e43ecdf95"))
                .conversationId(UUID.fromString("5bddb194-5ca2-4461-9b6b-c5f986fa86ea"))
                .status(ExecutionStatusDTO.ACCEPTED)
                .acceptedAt(OffsetDateTime.parse("2026-04-29T10:00:00Z"))
                .idempotencyKey("key-1")
                .idempotencyReplayed(true)
                .build();
    }

    private SubmitChatExecutionResponse getSubmitChatExecutionResponse() {
        return SubmitChatExecutionResponse.builder()
                .executionId(UUID.fromString("d8827667-03f3-4d46-ae0d-d35e43ecdf95"))
                .conversationId(UUID.fromString("5bddb194-5ca2-4461-9b6b-c5f986fa86ea"))
                .state("QUEUED")
                .createdAt(OffsetDateTime.parse("2026-04-29T10:00:00Z"))
                .idempotencyKey("key-1")
                .idempotencyReplayed(true)
                .build();
    }

    private ChatExecutionDTO getFailedChatExecutionDto() {
        return ChatExecutionDTO.builder()
                .executionId(UUID.fromString("d8827667-03f3-4d46-ae0d-d35e43ecdf95"))
                .conversationId(UUID.fromString("5bddb194-5ca2-4461-9b6b-c5f986fa86ea"))
                .status(ExecutionStatusDTO.FAILED)
                .error(this.getChatExecutionFailureDto())
                .build();
    }

    private ChatExecutionFailureDTO getChatExecutionFailureDto() {
        return ChatExecutionFailureDTO.builder()
                .code("EXECUTION_ERROR")
                .message("Execution failed")
                .details(java.util.Map.of("retryable", true))
                .build();
    }
}
