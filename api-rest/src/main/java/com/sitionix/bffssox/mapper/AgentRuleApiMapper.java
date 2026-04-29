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
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgentRuleApiMapper {

    @Mapping(target = "content", source = "text")
    AgentRuleDTO asAgentRuleDto(AgentRule src);

    AgentRulesResponseDTO asAgentRulesResponseDto(AgentRulesResponse src);

    @Mapping(target = "text", source = "content")
    CreateAgentRuleRequest asCreateAgentRuleRequest(CreateAgentRuleRequestDTO src);

    @Mapping(target = "text", source = "content")
    PatchAgentRuleRequest asPatchAgentRuleRequest(PatchAgentRuleRequestDTO src);

    AcceptAgentRuleRequest asAcceptAgentRuleRequest(AcceptAgentRuleRequestDTO src);

    DeleteAgentRuleResponseDTO asDeleteAgentRuleResponseDto(DeleteAgentRuleResponse src);

    default GetAgentRulesQuery asGetAgentRulesQuery(final AgentRuleStatusDTO status, final AgentRuleAuthorTypeDTO authorType) {
        final String statusFilter = status == null ? "ACTIVE" : status.getValue();
        final String authorTypeFilter = authorType == null ? null : authorType.getValue();
        return new GetAgentRulesQuery(statusFilter, authorTypeFilter);
    }
}
