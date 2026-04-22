package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentConversationMessageDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import java.time.OffsetDateTime;
import java.util.List;
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
        this.mapper = new ChatAgentApiMapperImpl();
    }

    @Test
    void givenChatAgentRequestDto_whenAsChatAgentRequest_thenReturnChatAgentRequest() {
        //given
        final ChatAgentRequestDTO given = this.getChatAgentRequestDto("Explain clean architecture");
        final ChatAgentRequest expected = this.getChatAgentRequest("Explain clean architecture");

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
                        AgentConversationDTO.TypeEnum.DIRECT,
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
                ))
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
                ))
        );

        //when
        final AgentConversationDetailsDTO actual = this.mapper.asAgentConversationDetailsDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private ChatAgentRequestDTO getChatAgentRequestDto(final String message) {
        return ChatAgentRequestDTO.builder()
                .message(message)
                .build();
    }

    private ChatAgentRequest getChatAgentRequest(final String message) {
        return ChatAgentRequest.builder()
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
                .reply(reply)
                .build();
    }

    private AgentConversationsResponse getAgentConversationsResponse(final List<AgentConversation> items) {
        return AgentConversationsResponse.builder()
                .items(items)
                .build();
    }

    private AgentConversationsResponseDTO getAgentConversationsResponseDto(final List<AgentConversationDTO> items) {
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
}
