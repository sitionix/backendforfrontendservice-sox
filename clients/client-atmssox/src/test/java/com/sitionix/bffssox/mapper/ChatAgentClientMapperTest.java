package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentResponseDTO;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
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
        final ChatAgentResponseDTO given = ChatAgentResponseDTO.builder()
                .reply("SOLID is a set of design principles.")
                .build();
        final ChatAgentResponse expected = ChatAgentResponse.builder()
                .reply("SOLID is a set of design principles.")
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
}
