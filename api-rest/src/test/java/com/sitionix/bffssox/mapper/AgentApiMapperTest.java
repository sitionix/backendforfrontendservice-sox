package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.AgentStatus;
import com.sitionix.bffssox.domain.AgentProjectStatus;
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
class AgentApiMapperTest {

    private AgentApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new AgentApiMapperImpl();
    }

    @Test
    void givenAgent_whenAsAgentDto_thenReturnAgentDto() {
        //given
        final Agent given = this.agent();
        final AgentDTO expected = this.agentDto();

        //when
        final AgentDTO actual = this.mapper.asAgentDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentsResponse_whenAsAgentsResponseDto_thenReturnAgentsResponseDto() {
        //given
        final AgentsResponse given = AgentsResponse.builder()
                .items(List.of(this.agent()))
                .build();
        final AgentsResponseDTO expected = AgentsResponseDTO.builder()
                .items(List.of(this.agentDto()))
                .build();

        //when
        final AgentsResponseDTO actual = this.mapper.asAgentsResponseDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullAgent_whenAsAgentDto_thenReturnNull() {
        //given
        final Agent given = null;

        //when
        final AgentDTO actual = this.mapper.asAgentDto(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenNullAgentsResponse_whenAsAgentsResponseDto_thenReturnNull() {
        //given
        final AgentsResponse given = null;

        //when
        final AgentsResponseDTO actual = this.mapper.asAgentsResponseDto(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenAgentProject_whenAsAgentProjectDto_thenReturnAgentProjectDto() {
        //given
        final AgentProject given = AgentProject.builder()
                .id(UUID.fromString("f2f2b8c4-5039-4095-b5ec-d584bd429ca3"))
                .name("Project")
                .description("Description")
                .status(AgentProjectStatus.ACTIVE)
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();

        //when
        final AgentProjectDTO actual = this.mapper.asAgentProjectDto(given);

        //then
        assertThat(actual.getId()).isEqualTo(UUID.fromString("f2f2b8c4-5039-4095-b5ec-d584bd429ca3"));
        assertThat(actual.getName()).isEqualTo("Project");
        assertThat(actual.getDescription()).isEqualTo("Description");
        assertThat(actual.getStatus().toString()).isEqualTo("ACTIVE");
        assertThat(actual.getCreatedAt()).isEqualTo(OffsetDateTime.parse("2026-04-10T10:00:00Z"));
        assertThat(actual.getUpdatedAt()).isEqualTo(OffsetDateTime.parse("2026-04-10T10:01:00Z"));
    }

    @Test
    void givenAgentProjectsPageResponse_whenAsAgentProjectsPageResponseDto_thenReturnDto() {
        //given
        final AgentProjectsPageResponse given = AgentProjectsPageResponse.builder()
                .items(List.of(AgentProject.builder()
                        .id(UUID.fromString("f2f2b8c4-5039-4095-b5ec-d584bd429ca3"))
                        .name("Project")
                        .description("Description")
                        .status(AgentProjectStatus.ACTIVE)
                        .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                        .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                        .build()))
                .page(0)
                .size(20)
                .hasNext(false)
                .build();

        //when
        final AgentProjectsPageResponseDTO actual = this.mapper.asAgentProjectsPageResponseDto(given);

        //then
        assertThat(actual.getItems()).hasSize(1);
        assertThat(actual.getPage()).isEqualTo(0);
        assertThat(actual.getSize()).isEqualTo(20);
        assertThat(actual.getHasNext()).isFalse();
    }

    @Test
    void givenAgentWithArchivedStatusAndNullId_whenAsAgentDto_thenReturnArchivedStatusAndNullId() {
        //given
        final Agent given = Agent.builder()
                .id(null)
                .name("A")
                .description("D")
                .instruction("I")
                .status(AgentStatus.ARCHIVED)
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();

        //when
        final AgentDTO actual = this.mapper.asAgentDto(given);

        //then
        assertThat(actual.getId()).isNull();
        assertThat(actual.getStatus()).isEqualTo(AgentDTO.StatusEnum.ARCHIVED);
    }

    private Agent agent() {
        return Agent.builder()
                .id("f2f2b8c4-5039-4095-b5ec-d584bd429ca3")
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .instruction("Check boundaries first")
                .status(AgentStatus.ACTIVE)
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();
    }

    private AgentDTO agentDto() {
        return AgentDTO.builder()
                .id(UUID.fromString("f2f2b8c4-5039-4095-b5ec-d584bd429ca3"))
                .name("Architecture Reviewer")
                .description("Checks architecture decisions")
                .instruction("Check boundaries first")
                .status(AgentDTO.StatusEnum.ACTIVE)
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();
    }
}
