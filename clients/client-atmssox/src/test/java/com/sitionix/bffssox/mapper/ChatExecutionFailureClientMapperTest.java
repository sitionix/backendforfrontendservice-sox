package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.ChatExecutionFailureDTO;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChatExecutionFailureClientMapperTest {

    private ChatExecutionFailureClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ChatExecutionFailureClientMapperImpl();
    }

    @Test
    void givenChatExecutionFailureDto_whenAsChatExecutionFailure_thenReturnMappedDomain() {
        //given
        final ChatExecutionFailureDTO given = ChatExecutionFailureDTO.builder()
                .code("EXECUTION_ERROR")
                .message("Execution failed")
                .details(Map.of("retryable", true))
                .build();

        //when
        final ChatExecutionFailure actual = this.mapper.asChatExecutionFailure(given);

        //then
        assertThat(actual).isEqualTo(ChatExecutionFailure.builder()
                .failureClass("EXECUTION_ERROR")
                .reason("Execution failed")
                .retryable(true)
                .build());
    }

    @Test
    void givenNullFailureDto_whenAsChatExecutionFailure_thenReturnNull() {
        //given
        final ChatExecutionFailureDTO given = null;

        //when
        final ChatExecutionFailure actual = this.mapper.asChatExecutionFailure(given);

        //then
        assertThat(actual).isNull();
    }
}
