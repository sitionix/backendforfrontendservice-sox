package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PatchAgentClientMapperTest {

    private PatchAgentClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new PatchAgentClientMapperImpl();
    }

    @Test
    void givenPatchAgentRequest_whenAsPatchAgentRequestDto_thenReturnPatchAgentRequestDto() {
        //given
        final PatchAgentRequest given = PatchAgentRequest.builder()
                .name("Updated Architecture Reviewer")
                .description("Updated description")
                .instruction("Updated instruction")
                .build();
        final PatchAgentRequestDTO expected = PatchAgentRequestDTO.builder()
                .name("Updated Architecture Reviewer")
                .description(JsonNullable.of("Updated description"))
                .instruction(JsonNullable.of("Updated instruction"))
                .build();

        //when
        final PatchAgentRequestDTO actual = this.mapper.asPatchAgentRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullPatchAgentRequest_whenAsPatchAgentRequestDto_thenReturnNull() {
        //given
        final PatchAgentRequest given = null;

        //when
        final PatchAgentRequestDTO actual = this.mapper.asPatchAgentRequestDto(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenPatchAgentRequestWithOnlyName_whenAsPatchAgentRequestDto_thenReturnUndefinedOptionalFields() {
        //given
        final PatchAgentRequest given = PatchAgentRequest.builder()
                .name("Updated Architecture Reviewer")
                .description(null)
                .instruction(null)
                .build();

        //when
        final PatchAgentRequestDTO actual = this.mapper.asPatchAgentRequestDto(given);

        //then
        assertThat(actual.getDescription_JsonNullable()).isEqualTo(JsonNullable.undefined());
        assertThat(actual.getInstruction_JsonNullable()).isEqualTo(JsonNullable.undefined());
    }
}
