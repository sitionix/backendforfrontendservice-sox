package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentStatus;
import com.sitionix.bffssox.domain.AgentsResponse;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AgentClientMapperTest {

    private AgentClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new AgentClientMapperImpl();
    }

    @Test
    void givenAgentDto_whenAsAgent_thenReturnAgent() {
        //given
        final AgentDTO given = this.agentDto();
        final Agent expected = this.agent();

        //when
        final Agent actual = this.mapper.asAgent(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentsResponseDto_whenAsAgentsResponse_thenReturnAgentsResponse() {
        //given
        final AgentsResponseDTO given = this.agentsResponseDto();
        final AgentsResponse expected = this.agentsResponse();

        //when
        final AgentsResponse actual = this.mapper.asAgentsResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullAgentDto_whenAsAgent_thenReturnNull() {
        //given
        final AgentDTO given = null;

        //when
        final Agent actual = this.mapper.asAgent(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenNullAgentsResponseDto_whenAsAgentsResponse_thenReturnNull() {
        //given
        final AgentsResponseDTO given = null;

        //when
        final AgentsResponse actual = this.mapper.asAgentsResponse(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenAgentProjectDto_whenAsAgentProject_thenReturnAgentProject() {
        //given
        final AgentProjectDTO given = this.agentProjectDto();

        //when
        final AgentProject actual = this.mapper.asAgentProject(given);

        //then
        assertThat(actual.getId()).isEqualTo(UUID.fromString("9df8ca36-d8c8-4703-9f8c-c8d50b5d4794"));
        assertThat(actual.getName()).isEqualTo("Project");
        assertThat(actual.getDescription()).isEqualTo("Description");
        assertThat(actual.getContext()).isEqualTo("Context");
        assertThat(actual.getStatus()).isNull();
        assertThat(actual.getCreatedAt()).isEqualTo(OffsetDateTime.parse("2026-04-10T10:00:00Z"));
        assertThat(actual.getUpdatedAt()).isEqualTo(OffsetDateTime.parse("2026-04-10T10:01:00Z"));
    }

    @Test
    void givenAgentProjectsPageResponseDto_whenAsAgentProjectsPageResponse_thenReturnPageResponse() {
        //given
        final AgentProjectsPageResponseDTO given = this.agentProjectsPageResponseDto();

        //when
        final AgentProjectsPageResponse actual = this.mapper.asAgentProjectsPageResponse(given);

        //then
        assertThat(actual.getItems()).hasSize(1);
        assertThat(actual.getPage()).isEqualTo(1);
        assertThat(actual.getSize()).isEqualTo(10);
        assertThat(actual.getHasNext()).isTrue();
    }

    private AgentDTO agentDto() {
        return AgentDTO.builder()
                .id(UUID.fromString("9df8ca36-d8c8-4703-9f8c-c8d50b5d4794"))
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .status(AgentDTO.StatusEnum.ARCHIVED)
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();
    }

    private Agent agent() {
        return Agent.builder()
                .id("9df8ca36-d8c8-4703-9f8c-c8d50b5d4794")
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .status(AgentStatus.ARCHIVED)
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();
    }

    private AgentProjectDTO agentProjectDto() {
        return AgentProjectDTO.builder()
                .id(UUID.fromString("9df8ca36-d8c8-4703-9f8c-c8d50b5d4794"))
                .name("Project")
                .description("Description")
                .context("Context")
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();
    }

    private AgentProjectsPageResponseDTO agentProjectsPageResponseDto() {
        return AgentProjectsPageResponseDTO.builder()
                .items(List.of(this.agentProjectDto()))
                .page(1)
                .size(10)
                .hasNext(true)
                .build();
    }

    private AgentsResponseDTO agentsResponseDto() {
        return AgentsResponseDTO.builder()
                .items(List.of(this.agentDto()))
                .build();
    }

    private AgentsResponse agentsResponse() {
        return AgentsResponse.builder()
                .items(List.of(this.agent()))
                .build();
    }
}
