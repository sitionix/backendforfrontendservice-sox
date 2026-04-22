package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentConversationMessageDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentResponseDTO;
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
class ChatAgentClientMapperTest {

    private ChatAgentClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ChatAgentClientMapperImpl();
    }

    @Test
    void givenChatAgentRequest_whenAsChatAgentRequestDto_thenReturnChatAgentRequestDto() {
        //given
        final ChatAgentRequest given = ChatAgentRequest.builder()
                .message("Explain SOLID")
                .build();
        final ChatAgentRequestDTO expected = ChatAgentRequestDTO.builder()
                .message("Explain SOLID")
                .build();

        //when
        final ChatAgentRequestDTO actual = this.mapper.asChatAgentRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatAgentResponseDto_whenAsChatAgentResponse_thenReturnChatAgentResponse() {
        //given
        final AgentConversationMessageDTO replyDto = AgentConversationMessageDTO.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .authorType(AgentConversationMessageDTO.AuthorTypeEnum.AGENT)
                .authorId("4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                .content("SOLID is a set of design principles.")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        final ChatAgentResponseDTO given = ChatAgentResponseDTO.builder()
                .conversationId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .reply(replyDto)
                .build();
        final ChatAgentMessage reply = ChatAgentMessage.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .authorType("AGENT")
                .authorId("4e0c95eb-9e63-4b3f-98f4-2c8c713233c0")
                .content("SOLID is a set of design principles.")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        final ChatAgentResponse expected = ChatAgentResponse.builder()
                .conversationId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .reply(reply)
                .build();

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
        final AgentConversationDTO conversationDto = AgentConversationDTO.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Explain clean architecture")
                .type(AgentConversationDTO.TypeEnum.DIRECT)
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .lastMessageAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        final AgentConversationsResponseDTO given = AgentConversationsResponseDTO.builder()
                .items(List.of(conversationDto))
                .build();
        final AgentConversation conversation = AgentConversation.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Explain clean architecture")
                .type("DIRECT")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .lastMessageAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        final AgentConversationsResponse expected = AgentConversationsResponse.builder()
                .items(List.of(conversation))
                .build();

        //when
        final AgentConversationsResponse actual = this.mapper.asAgentConversationsResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentConversationDetailsDto_whenAsAgentConversationDetails_thenReturnMappedDetails() {
        //given
        final AgentConversationMessageDTO messageDto = AgentConversationMessageDTO.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .authorType(AgentConversationMessageDTO.AuthorTypeEnum.AGENT)
                .authorId("agent-1")
                .content("Clean architecture separates business logic.")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .build();
        final AgentConversationDetailsDTO given = AgentConversationDetailsDTO.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Explain clean architecture")
                .type(AgentConversationDetailsDTO.TypeEnum.DIRECT)
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .lastMessageAt(OffsetDateTime.parse("2026-04-21T10:01:00Z"))
                .messages(List.of(messageDto))
                .build();

        //when
        final AgentConversationDetails actual = this.mapper.asAgentConversationDetails(given);

        //then
        assertThat(actual.getId()).isEqualTo(given.getId());
        assertThat(actual.getType()).isEqualTo("DIRECT");
        assertThat(actual.getMessages()).hasSize(1);
        assertThat(actual.getMessages().get(0).getAuthorType()).isEqualTo("AGENT");
    }
}
