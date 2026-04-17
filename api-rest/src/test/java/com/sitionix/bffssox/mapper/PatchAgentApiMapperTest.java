package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PatchAgentApiMapperTest {

    private PatchAgentApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new PatchAgentApiMapperImpl();
    }

    @Test
    void givenPatchAgentRequestDto_whenAsPatchAgentRequest_thenReturnPatchAgentRequest() {
        //given
        final PatchAgentRequestDTO given = PatchAgentRequestDTO.builder()
                .name("Updated Architecture Reviewer")
                .description(JsonNullable.of("Updated description"))
                .instruction(JsonNullable.of("Updated instruction"))
                .build();
        final PatchAgentRequest expected = PatchAgentRequest.builder()
                .name("Updated Architecture Reviewer")
                .description("Updated description")
                .instruction("Updated instruction")
                .build();

        //when
        final PatchAgentRequest actual = this.mapper.asPatchAgentRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullPatchAgentRequestDto_whenAsPatchAgentRequest_thenReturnNull() {
        //given
        final PatchAgentRequestDTO given = null;

        //when
        final PatchAgentRequest actual = this.mapper.asPatchAgentRequest(given);

        //then
        assertThat(actual).isNull();
    }
}
