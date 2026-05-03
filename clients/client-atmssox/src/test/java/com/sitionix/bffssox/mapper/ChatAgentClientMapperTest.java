package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentConversationMessageDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentExecutionDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.client.dto.ChatExecutionFailureDTO;
import com.app_afesox.atmssox.client.dto.ExecutionStatusDTO;
import com.app_afesox.atmssox.client.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatAgentClientMapperTest {

    private ChatAgentClientMapper mapper;

    @Mock
    private ChatExecutionStatusClientMapper chatExecutionStatusClientMapper;

    @Mock
    private ChatExecutionFailureClientMapper chatExecutionFailureClientMapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ChatAgentClientMapperImpl(
                this.chatExecutionStatusClientMapper,
                this.chatExecutionFailureClientMapper
        );
    }

    @Test
    void givenChatAgentRequest_whenAsChatAgentRequestDto_thenReturnChatAgentRequestDto() {
        //given
        final UUID clientRequestId = UUID.fromString("6ba1153e-a336-42a1-92ea-3203be095aa2");
        final ChatAgentRequest given = this.getChatAgentRequest(clientRequestId, "Explain SOLID");
        final ChatAgentRequestDTO expected = this.getChatAgentRequestDto(clientRequestId, "Explain SOLID");

        //when
        final ChatAgentRequestDTO actual = this.mapper.asChatAgentRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatAgentExecutionDto_whenAsChatAgentResponse_thenReturnChatAgentResponse() {
        //given
        final UUID conversationId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-21T10:01:00Z");
        final ChatAgentExecutionDTO given = this.getChatAgentExecutionDto(
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
                this.getChatAgentMessage(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        "AGENT",
                        "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0",
                        "SOLID is a set of design principles.",
                        createdAt
                )
        );

        //when
        final ChatAgentResponse actual = this.mapper.asChatAgentResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
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
    void givenNullChatAgentExecutionDto_whenAsChatAgentResponse_thenReturnNull() {
        //given
        final ChatAgentExecutionDTO given = null;

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
                        AgentConversationDTO.TypeEnum.DIRECT,
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
        when(this.chatExecutionStatusClientMapper.mapExecutionStatus(ExecutionStatusDTO.ACCEPTED)).thenReturn("QUEUED");

        //when
        final SubmitChatExecutionResponse actual = this.mapper.asSubmitChatExecutionResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatExecutionDto_whenAsChatExecution_thenReturnMappedDomain() {
        //given
        final ChatExecutionDTO given = this.getFailedChatExecutionDto();
        final ChatExecutionFailureDTO failureDTO = given.getError();
        when(this.chatExecutionStatusClientMapper.mapExecutionStatus(ExecutionStatusDTO.FAILED)).thenReturn("FAILED");
        when(this.chatExecutionFailureClientMapper.asChatExecutionFailure(failureDTO))
                .thenReturn(this.getChatExecutionFailure("EXECUTION_ERROR", "Execution failed", true));

        //when
        final ChatExecution actual = this.mapper.asChatExecution(given);

        //then
        assertThat(actual.getState()).isEqualTo("FAILED");
        assertThat(actual.getFailure().getFailureClass()).isEqualTo("EXECUTION_ERROR");
        assertThat(actual.getFailure().getReason()).isEqualTo("Execution failed");
        assertThat(actual.getFailure().getRetryable()).isTrue();
    }

    private ChatAgentRequest getChatAgentRequest(final UUID clientRequestId, final String message) {
        return ChatAgentRequest.builder()
                .clientRequestId(clientRequestId)
                .message(message)
                .build();
    }

    private ChatAgentRequestDTO getChatAgentRequestDto(final UUID clientRequestId, final String message) {
        return ChatAgentRequestDTO.builder()
                .clientRequestId(clientRequestId)
                .message(message)
                .build();
    }

    private ChatAgentExecutionDTO getChatAgentExecutionDto(final UUID conversationId, final AgentConversationMessageDTO reply) {
        return ChatAgentExecutionDTO.builder()
                .conversationId(conversationId)
                .assistantMessage(reply)
                .build();
    }

    private ChatAgentResponse getChatAgentResponse(final UUID conversationId, final ChatAgentMessage reply) {
        return ChatAgentResponse.builder()
                .conversationId(conversationId)
                .reply(reply)
                .build();
    }

    private AgentConversationsResponseDTO getAgentConversationsResponseDto(final List<AgentConversationDTO> items) {
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

    private AgentConversationDTO getAgentConversationDto(
            final UUID id,
            final String title,
            final AgentConversationDTO.TypeEnum type,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        return AgentConversationDTO.builder()
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
                .inputMessageId(UUID.fromString("f0beec7e-5c98-48b9-ae82-0a6952576a7a"))
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
                .inputMessageId(UUID.fromString("f0beec7e-5c98-48b9-ae82-0a6952576a7a"))
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

    private ChatExecutionFailure getChatExecutionFailure(
            final String failureClass,
            final String reason,
            final Boolean retryable
    ) {
        return ChatExecutionFailure.builder()
                .failureClass(failureClass)
                .reason(reason)
                .retryable(retryable)
                .build();
    }
}
