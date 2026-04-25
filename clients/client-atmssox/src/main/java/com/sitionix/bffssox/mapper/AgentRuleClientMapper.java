package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AcceptAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.AgentRulesResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRuleRequestDTO;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentRuleClientMapper {

    AgentRule asAgentRule(AgentRuleDTO src);

    AgentRulesResponse asAgentRulesResponse(AgentRulesResponseDTO src);

    CreateAgentRuleRequestDTO asCreateAgentRuleRequestDto(CreateAgentRuleRequest src);

    PatchAgentRuleRequestDTO asPatchAgentRuleRequestDto(PatchAgentRuleRequest src);

    AcceptAgentRuleRequestDTO asAcceptAgentRuleRequestDto(AcceptAgentRuleRequest src);

    DeleteAgentRuleResponse asDeleteAgentRuleResponse(DeleteAgentRuleResponseDTO src);
}
