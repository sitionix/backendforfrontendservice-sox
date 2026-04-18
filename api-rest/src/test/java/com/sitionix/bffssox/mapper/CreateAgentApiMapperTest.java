package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateAgentApiMapperTest {

    private CreateAgentApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CreateAgentApiMapperImpl();
    }

    @Test
    void givenCreateAgentRequestDto_whenAsCreateAgentRequest_thenReturnCreateAgentRequest() {
        //given
        final CreateAgentRequestDTO given = CreateAgentRequestDTO.builder()
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .build();
        final CreateAgentRequest expected = CreateAgentRequest.builder()
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .build();

        //when
        final CreateAgentRequest actual = this.mapper.asCreateAgentRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullCreateAgentRequestDto_whenAsCreateAgentRequest_thenReturnNull() {
        //given
        final CreateAgentRequestDTO given = null;

        //when
        final CreateAgentRequest actual = this.mapper.asCreateAgentRequest(given);

        //then
        assertThat(actual).isNull();
    }
}
