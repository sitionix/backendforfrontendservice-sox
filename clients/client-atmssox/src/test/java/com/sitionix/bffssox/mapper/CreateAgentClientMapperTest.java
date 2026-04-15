package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateAgentClientMapperTest {

    private CreateAgentClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CreateAgentClientMapperImpl();
    }

    @Test
    void givenCreateAgentRequest_whenAsCreateAgentRequestDto_thenReturnCreateAgentRequestDto() {
        //given
        final CreateAgentRequest given = CreateAgentRequest.builder()
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .build();
        final CreateAgentRequestDTO expected = CreateAgentRequestDTO.builder()
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .build();

        //when
        final CreateAgentRequestDTO actual = this.mapper.asCreateAgentRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullCreateAgentRequest_whenAsCreateAgentRequestDto_thenReturnNull() {
        //given
        final CreateAgentRequest given = null;

        //when
        final CreateAgentRequestDTO actual = this.mapper.asCreateAgentRequestDto(given);

        //then
        assertThat(actual).isNull();
    }
}
