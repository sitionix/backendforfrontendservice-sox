package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
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
        final ChatAgentRequestDTO given = ChatAgentRequestDTO.builder()
                .message("Explain clean architecture")
                .build();
        final ChatAgentRequest expected = ChatAgentRequest.builder()
                .message("Explain clean architecture")
                .build();

        //when
        final ChatAgentRequest actual = this.mapper.asChatAgentRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenChatAgentResponse_whenAsChatAgentResponseDto_thenReturnChatAgentResponseDto() {
        //given
        final ChatAgentResponse given = ChatAgentResponse.builder()
                .reply("Clean architecture separates business rules from frameworks.")
                .build();
        final ChatAgentResponseDTO expected = ChatAgentResponseDTO.builder()
                .reply("Clean architecture separates business rules from frameworks.")
                .build();

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
}
