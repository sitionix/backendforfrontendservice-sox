package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentConversationMessageDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
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
        final ChatAgentRequestDTO given = this.getChatAgentRequestDto();
        final ChatAgentRequest expected = this.getChatAgentRequest();

        //when
        final ChatAgentRequest actual = this.mapper.asChatAgentRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatAgentResponse_whenAsChatAgentResponseDto_thenReturnChatAgentResponseDto() {
        //given
        final ChatAgentResponse given = this.getChatAgentResponse();
        final ChatAgentResponseDTO expected = this.getChatAgentResponseDto();

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
        final AgentConversationsResponse given = this.getAgentConversationsResponse();
        final AgentConversationsResponseDTO expected = this.getAgentConversationsResponseDto();

        //when
        final AgentConversationsResponseDTO actual = this.mapper.asAgentConversationsResponseDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentConversationDetails_whenAsAgentConversationDetailsDto_thenReturnMappedDetails() {
        //given
        final AgentConversationDetails given = this.getAgentConversationDetails();
        final AgentConversationDetailsDTO expected = this.getAgentConversationDetailsDto();

        //when
        final AgentConversationDetailsDTO actual = this.mapper.asAgentConversationDetailsDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private ChatAgentRequestDTO getChatAgentRequestDto() {
        return ChatAgentRequestDTO.builder()
                .message("Explain clean architecture")
                .build();
    }

    private ChatAgentRequest getChatAgentRequest() {
        return ChatAgentRequest.builder()
                .message("Explain clean architecture")
                .build();
    }

    private ChatAgentResponse getChatAgentResponse() {
        final ChatAgentMessage message = ChatAgentMessage.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .authorType("AGENT")
                .authorId("4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                .content("Clean architecture separates business rules from frameworks.")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        return ChatAgentResponse.builder()
                .conversationId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .reply(message)
                .build();
    }

    private ChatAgentResponseDTO getChatAgentResponseDto() {
        final AgentConversationMessageDTO replyDto = AgentConversationMessageDTO.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .authorType(AgentConversationMessageDTO.AuthorTypeEnum.AGENT)
                .authorId("4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                .content("Clean architecture separates business rules from frameworks.")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        return ChatAgentResponseDTO.builder()
                .conversationId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .reply(replyDto)
                .build();
    }

    private AgentConversationsResponse getAgentConversationsResponse() {
        final AgentConversation conversation = AgentConversation.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Explain clean architecture")
                .type("DIRECT")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .lastMessageAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        return AgentConversationsResponse.builder()
                .items(List.of(conversation))
                .build();
    }

    private AgentConversationsResponseDTO getAgentConversationsResponseDto() {
        final AgentConversation conversation = AgentConversation.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Explain clean architecture")
                .type("DIRECT")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .lastMessageAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        return AgentConversationsResponseDTO.builder()
                .items(List.of(this.mapper.asAgentConversationDto(conversation)))
                .build();
    }

    private AgentConversationDetails getAgentConversationDetails() {
        final ChatAgentMessage message = ChatAgentMessage.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .authorType("AGENT")
                .authorId("agent-1")
                .content("Clean architecture separates business logic.")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        return AgentConversationDetails.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Explain clean architecture")
                .type("DIRECT")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .lastMessageAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .messages(List.of(message))
                .build();
    }

    private AgentConversationDetailsDTO getAgentConversationDetailsDto() {
        return AgentConversationDetailsDTO.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Explain clean architecture")
                .type(AgentConversationDetailsDTO.TypeEnum.DIRECT)
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .lastMessageAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .messages(List.of(AgentConversationMessageDTO.builder()
                        .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                        .authorType(AgentConversationMessageDTO.AuthorTypeEnum.AGENT)
                        .authorId("agent-1")
                        .content("Clean architecture separates business logic.")
                        .createdAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                        .build()))
                .build();
    }
}
