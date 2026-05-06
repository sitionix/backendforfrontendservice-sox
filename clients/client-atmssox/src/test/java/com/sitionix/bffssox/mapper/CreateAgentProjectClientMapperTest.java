package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.CreateAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateAgentProjectClientMapperTest {

    private CreateAgentProjectClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CreateAgentProjectClientMapperImpl();
    }

    @Test
    void givenCreateAgentProjectRequest_whenAsCreateAgentProjectRequestDto_thenReturnDto() {
        //given
        final CreateAgentProjectRequest given = this.createAgentProjectRequest();
        final CreateAgentProjectRequestDTO expected = this.createAgentProjectRequestDto();

        //when
        final CreateAgentProjectRequestDTO actual = this.mapper.asCreateAgentProjectRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullCreateAgentProjectRequest_whenAsCreateAgentProjectRequestDto_thenReturnNull() {
        //given
        final CreateAgentProjectRequest given = null;

        //when
        final CreateAgentProjectRequestDTO actual = this.mapper.asCreateAgentProjectRequestDto(given);

        //then
        assertThat(actual).isNull();
    }

    private CreateAgentProjectRequest createAgentProjectRequest() {
        return CreateAgentProjectRequest.builder()
                .name("Project")
                .description("Description")
                .build();
    }

    private CreateAgentProjectRequestDTO createAgentProjectRequestDto() {
        return CreateAgentProjectRequestDTO.builder()
                .name("Project")
                .description("Description")
                .build();
    }
}
