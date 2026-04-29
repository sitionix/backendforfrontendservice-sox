package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.AcceptAgentRuleRequestDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRuleAuthorTypeDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRuleDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRuleStatusDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRulesResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.bffssox.api_first.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentRuleRequestDTO;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentRuleApiMapper {

    AgentRuleDTO asAgentRuleDto(AgentRule src);

    AgentRulesResponseDTO asAgentRulesResponseDto(AgentRulesResponse src);

    CreateAgentRuleRequest asCreateAgentRuleRequest(CreateAgentRuleRequestDTO src);

    PatchAgentRuleRequest asPatchAgentRuleRequest(PatchAgentRuleRequestDTO src);

    AcceptAgentRuleRequest asAcceptAgentRuleRequest(AcceptAgentRuleRequestDTO src);

    DeleteAgentRuleResponseDTO asDeleteAgentRuleResponseDto(DeleteAgentRuleResponse src);

    default GetAgentRulesQuery asGetAgentRulesQuery(final AgentRuleStatusDTO status, final AgentRuleAuthorTypeDTO authorType) {
        final String statusFilter = status == null ? "ACTIVE" : status.getValue();
        final String authorTypeFilter = authorType == null ? null : authorType.getValue();
        return new GetAgentRulesQuery(statusFilter, authorTypeFilter);
    }
}
