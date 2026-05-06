package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentRuleApi;
import com.app_afesox.atmssox.client.dto.AcceptAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleAuthorTypeDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleStatusDTO;
import com.app_afesox.atmssox.client.dto.AgentRulesResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRuleRequestDTO;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import com.sitionix.bffssox.mapper.AgentRuleClientMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentRuleAtmssoxClient {

    private final AgentRuleApi agentRuleApi;
    private final AgentRuleClientMapper agentRuleClientMapper;
    private final AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    public AgentRulesResponse getAgentRules(final UUID agentId, final GetAgentRulesQuery query) {
        final AgentRuleStatusDTO status = query == null || query.status() == null ? null : AgentRuleStatusDTO.fromValue(query.status());
        final AgentRuleAuthorTypeDTO authorType = query == null || query.authorType() == null
                ? null
                : AgentRuleAuthorTypeDTO.fromValue(query.authorType());
        final AgentRulesResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentRuleApi.getAgentRules(agentId, status, authorType)
        );
        return this.agentRuleClientMapper.asAgentRulesResponse(responseDTO);
    }

    public AgentRule createAgentRule(final UUID agentId, final CreateAgentRuleRequest request) {
        final CreateAgentRuleRequestDTO requestDTO = this.agentRuleClientMapper.asCreateAgentRuleRequestDto(request);
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentRuleApi.createAgentRule(agentId, requestDTO));
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    public AgentRule patchAgentRule(final UUID agentId, final UUID ruleId, final PatchAgentRuleRequest request) {
        final PatchAgentRuleRequestDTO requestDTO = this.agentRuleClientMapper.asPatchAgentRuleRequestDto(request);
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentRuleApi.patchAgentRule(agentId, ruleId, requestDTO)
        );
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    public AgentRule acceptAgentRule(final UUID agentId, final UUID ruleId, final AcceptAgentRuleRequest request) {
        final AcceptAgentRuleRequestDTO requestDTO = request == null ? null : this.agentRuleClientMapper.asAcceptAgentRuleRequestDto(request);
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentRuleApi.acceptAgentRule(agentId, ruleId, requestDTO)
        );
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    public AgentRule rejectAgentRule(final UUID agentId, final UUID ruleId) {
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentRuleApi.rejectAgentRule(agentId, ruleId));
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    public DeleteAgentRuleResponse deleteAgentRule(final UUID agentId, final UUID ruleId) {
        final DeleteAgentRuleResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentRuleApi.deleteAgentRule(agentId, ruleId)
        );
        return this.agentRuleClientMapper.asDeleteAgentRuleResponse(responseDTO);
    }
}
