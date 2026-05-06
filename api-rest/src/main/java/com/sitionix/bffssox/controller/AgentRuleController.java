package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AgentRuleApi;
import com.app_afesox.bffssox.api_first.dto.AcceptAgentRuleRequestDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRuleAuthorTypeDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRuleDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRuleStatusDTO;
import com.app_afesox.bffssox.api_first.dto.AgentRulesResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.bffssox.api_first.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentRuleRequestDTO;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.mapper.AgentRuleApiMapper;
import com.sitionix.bffssox.usecase.CreateAgentRule;
import com.sitionix.bffssox.usecase.DeleteAgentRule;
import com.sitionix.bffssox.usecase.GetAgentRules;
import com.sitionix.bffssox.usecase.PatchAgentRule;
import com.sitionix.bffssox.usecase.RejectAgentRule;
import com.sitionix.bffssox.usecase.AcceptAgentRule;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentRuleController implements AgentRuleApi {

    private final AgentRuleApiMapper agentRuleApiMapper;
    private final GetAgentRules getAgentRules;
    private final CreateAgentRule createAgentRule;
    private final PatchAgentRule patchAgentRule;
    private final DeleteAgentRule deleteAgentRule;
    private final AcceptAgentRule acceptAgentRule;
    private final RejectAgentRule rejectAgentRule;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentRulesResponseDTO> getAgentRules(final UUID agentId,
                                                               final AgentRuleStatusDTO status,
                                                               final AgentRuleAuthorTypeDTO authorType) {
        final AgentRulesResponse response = this.getAgentRules.execute(agentId, this.agentRuleApiMapper.asGetAgentRulesQuery(status, authorType));
        return ResponseEntity.ok(this.agentRuleApiMapper.asAgentRulesResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentRuleDTO> createAgentRule(final UUID agentId, @Valid final CreateAgentRuleRequestDTO createAgentRuleRequestDTO) {
        final CreateAgentRuleRequest request = this.agentRuleApiMapper.asCreateAgentRuleRequest(createAgentRuleRequestDTO);
        final AgentRule response = this.createAgentRule.execute(agentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.agentRuleApiMapper.asAgentRuleDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentRuleDTO> patchAgentRule(final UUID agentId,
                                                       final UUID ruleId,
                                                       @Valid final PatchAgentRuleRequestDTO patchAgentRuleRequestDTO) {
        final AgentRule response = this.patchAgentRule.execute(agentId, ruleId, this.agentRuleApiMapper.asPatchAgentRuleRequest(patchAgentRuleRequestDTO));
        return ResponseEntity.ok(this.agentRuleApiMapper.asAgentRuleDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeleteAgentRuleResponseDTO> deleteAgentRule(final UUID agentId, final UUID ruleId) {
        final DeleteAgentRuleResponse response = this.deleteAgentRule.execute(agentId, ruleId);
        return ResponseEntity.ok(this.agentRuleApiMapper.asDeleteAgentRuleResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentRuleDTO> acceptAgentRule(final UUID agentId,
                                                        final UUID ruleId,
                                                        @Valid final AcceptAgentRuleRequestDTO acceptAgentRuleRequestDTO) {
        final AgentRule response = this.acceptAgentRule.execute(agentId, ruleId, this.agentRuleApiMapper.asAcceptAgentRuleRequest(acceptAgentRuleRequestDTO));
        return ResponseEntity.ok(this.agentRuleApiMapper.asAgentRuleDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentRuleDTO> rejectAgentRule(final UUID agentId, final UUID ruleId) {
        final AgentRule response = this.rejectAgentRule.execute(agentId, ruleId);
        return ResponseEntity.ok(this.agentRuleApiMapper.asAgentRuleDto(response));
    }
}
