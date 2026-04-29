package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AgentRuleDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRulesResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.bffssox.api_first.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentRuleRequestDTO;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AgentRuleApiMapperTest {

    private AgentRuleApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new AgentRuleApiMapperImpl();
    }

    @Test
    void givenAgentRule_whenAsAgentRuleDto_thenReturnAgentRuleDto() {
        //given
        final AgentRule given = this.agentRule();
        final AgentRuleDTO expected = this.agentRuleDto();

        //when
        final AgentRuleDTO actual = this.mapper.asAgentRuleDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentRulesResponse_whenAsAgentRulesResponseDto_thenReturnDto() {
        //given
        final AgentRulesResponse given = new AgentRulesResponse(List.of(this.agentRule()));
        final AgentRulesResponseDTO expected = AgentRulesResponseDTO.builder()
                .items(List.of(this.agentRuleDto()))
                .build();

        //when
        final AgentRulesResponseDTO actual = this.mapper.asAgentRulesResponseDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenCreateAgentRuleRequestDto_whenAsCreateAgentRuleRequest_thenReturnDomainRequest() {
        //given
        final CreateAgentRuleRequestDTO given = CreateAgentRuleRequestDTO.builder()
                .content("Always validate input")
                .build();
        final CreateAgentRuleRequest expected = new CreateAgentRuleRequest("Always validate input");

        //when
        final CreateAgentRuleRequest actual = this.mapper.asCreateAgentRuleRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenPatchAgentRuleRequestDto_whenAsPatchAgentRuleRequest_thenReturnDomainRequest() {
        //given
        final PatchAgentRuleRequestDTO given = PatchAgentRuleRequestDTO.builder()
                .content("Keep structure explicit")
                .build();
        final PatchAgentRuleRequest expected = new PatchAgentRuleRequest("Keep structure explicit");

        //when
        final PatchAgentRuleRequest actual = this.mapper.asPatchAgentRuleRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenDeleteAgentRuleResponse_whenAsDeleteAgentRuleResponseDto_thenReturnDto() {
        //given
        final DeleteAgentRuleResponse given = new DeleteAgentRuleResponse("DELETED");
        final DeleteAgentRuleResponseDTO expected = DeleteAgentRuleResponseDTO.builder()
                .status(DeleteAgentRuleResponseDTO.StatusEnum.DELETED)
                .build();

        //when
        final DeleteAgentRuleResponseDTO actual = this.mapper.asDeleteAgentRuleResponseDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private AgentRule agentRule() {
        return new AgentRule(
                UUID.fromString("9a79f65b-ff40-4f39-acfe-a58f089c86f7"),
                "Always validate input",
                OffsetDateTime.parse("2026-04-21T10:00:00Z"),
                OffsetDateTime.parse("2026-04-21T10:00:00Z")
        );
    }

    private AgentRuleDTO agentRuleDto() {
        return AgentRuleDTO.builder()
                .id(UUID.fromString("9a79f65b-ff40-4f39-acfe-a58f089c86f7"))
                .content("Always validate input")
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .build();
    }
}
