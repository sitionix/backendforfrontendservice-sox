package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentRuleAuthorTypeDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleStatusDTO;
import com.app_afesox.atmssox.client.dto.AgentRulesResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRuleRequestDTO;
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
class AgentRuleClientMapperTest {

    private AgentRuleClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new AgentRuleClientMapperImpl();
    }

    @Test
    void givenAgentRuleDto_whenAsAgentRule_thenReturnDomainModel() {
        //given
        final AgentRuleDTO given = this.agentRuleDto();
        final AgentRule expected = this.agentRule();

        //when
        final AgentRule actual = this.mapper.asAgentRule(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenAgentRulesResponseDto_whenAsAgentRulesResponse_thenReturnDomainResponse() {
        //given
        final AgentRulesResponseDTO given = AgentRulesResponseDTO.builder()
                .items(List.of(this.agentRuleDto()))
                .build();
        final AgentRulesResponse expected = new AgentRulesResponse(List.of(this.agentRule()));

        //when
        final AgentRulesResponse actual = this.mapper.asAgentRulesResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenCreateAgentRuleRequest_whenAsCreateAgentRuleRequestDto_thenReturnClientRequest() {
        //given
        final CreateAgentRuleRequest given = new CreateAgentRuleRequest("Validation", "Always validate input");
        final CreateAgentRuleRequestDTO expected = CreateAgentRuleRequestDTO.builder()
                .title("Validation")
                .content("Always validate input")
                .build();

        //when
        final CreateAgentRuleRequestDTO actual = this.mapper.asCreateAgentRuleRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenPatchAgentRuleRequest_whenAsPatchAgentRuleRequestDto_thenReturnClientRequest() {
        //given
        final PatchAgentRuleRequest given = new PatchAgentRuleRequest("Determinism", "Keep output deterministic");
        final PatchAgentRuleRequestDTO expected = PatchAgentRuleRequestDTO.builder()
                .title("Determinism")
                .content("Keep output deterministic")
                .build();

        //when
        final PatchAgentRuleRequestDTO actual = this.mapper.asPatchAgentRuleRequestDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenDeleteAgentRuleResponseDto_whenAsDeleteAgentRuleResponse_thenReturnDomainResponse() {
        //given
        final DeleteAgentRuleResponseDTO given = DeleteAgentRuleResponseDTO.builder()
                .status(DeleteAgentRuleResponseDTO.StatusEnum.DELETED)
                .build();
        final DeleteAgentRuleResponse expected = new DeleteAgentRuleResponse("DELETED");

        //when
        final DeleteAgentRuleResponse actual = this.mapper.asDeleteAgentRuleResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private AgentRuleDTO agentRuleDto() {
        return AgentRuleDTO.builder()
                .id(UUID.fromString("7f4ef04a-5365-43cc-8d10-98ef51f35b8d"))
                .agentId(UUID.fromString("6f4ef04a-5365-43cc-8d10-98ef51f35b8d"))
                .title("Validation")
                .content("Always validate input")
                .status(AgentRuleStatusDTO.ACTIVE)
                .authorType(AgentRuleAuthorTypeDTO.USER)
                .createdAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-21T10:00:00Z"))
                .build();
    }

    private AgentRule agentRule() {
        return new AgentRule(
                UUID.fromString("7f4ef04a-5365-43cc-8d10-98ef51f35b8d"),
                UUID.fromString("6f4ef04a-5365-43cc-8d10-98ef51f35b8d"),
                "Validation",
                "Always validate input",
                "ACTIVE",
                "USER",
                OffsetDateTime.parse("2026-04-21T10:00:00Z"),
                OffsetDateTime.parse("2026-04-21T10:00:00Z")
        );
    }
}
