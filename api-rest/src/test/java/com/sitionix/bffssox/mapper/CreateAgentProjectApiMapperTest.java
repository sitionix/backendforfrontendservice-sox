package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.CreateAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateAgentProjectApiMapperTest {

    private CreateAgentProjectApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CreateAgentProjectApiMapperImpl();
    }

    @Test
    void givenCreateAgentProjectRequestDto_whenAsCreateAgentProjectRequest_thenReturnDomainObject() {
        //given
        final CreateAgentProjectRequestDTO given = this.createAgentProjectRequestDto();
        final CreateAgentProjectRequest expected = this.createAgentProjectRequest();

        //when
        final CreateAgentProjectRequest actual = this.mapper.asCreateAgentProjectRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullCreateAgentProjectRequestDto_whenAsCreateAgentProjectRequest_thenReturnNull() {
        //given
        final CreateAgentProjectRequestDTO given = null;

        //when
        final CreateAgentProjectRequest actual = this.mapper.asCreateAgentProjectRequest(given);

        //then
        assertThat(actual).isNull();
    }

    private CreateAgentProjectRequestDTO createAgentProjectRequestDto() {
        return CreateAgentProjectRequestDTO.builder()
                .name("Project")
                .description("Description")
                .build();
    }

    private CreateAgentProjectRequest createAgentProjectRequest() {
        return CreateAgentProjectRequest.builder()
                .name("Project")
                .description("Description")
                .build();
    }
}
