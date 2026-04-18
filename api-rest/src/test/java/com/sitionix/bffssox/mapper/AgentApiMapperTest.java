package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentsResponseDTO;
import com.sitionix.bffssox.domain.Agent;
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
