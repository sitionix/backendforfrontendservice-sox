package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AcceptAgentRuleRequestDTO;
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
import com.sitionix.bffssox.mapper.AgentRuleApiMapper;
import com.sitionix.bffssox.usecase.AcceptAgentRule;
import com.sitionix.bffssox.usecase.CreateAgentRule;
import com.sitionix.bffssox.usecase.DeleteAgentRule;
import com.sitionix.bffssox.usecase.GetAgentRules;
import com.sitionix.bffssox.usecase.PatchAgentRule;
import com.sitionix.bffssox.usecase.RejectAgentRule;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentRuleControllerTest {

    private AgentRuleController agentRuleController;

    @Mock private AgentRuleApiMapper agentRuleApiMapper;
    @Mock private GetAgentRules getAgentRules;
    @Mock private CreateAgentRule createAgentRule;
    @Mock private PatchAgentRule patchAgentRule;
    @Mock private DeleteAgentRule deleteAgentRule;
    @Mock private AcceptAgentRule acceptAgentRule;
    @Mock private RejectAgentRule rejectAgentRule;

    @BeforeEach
    void setUp() {
        this.agentRuleController = new AgentRuleController(this.agentRuleApiMapper, this.getAgentRules, this.createAgentRule,
                this.patchAgentRule, this.deleteAgentRule, this.acceptAgentRule, this.rejectAgentRule);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentRuleApiMapper, this.getAgentRules, this.createAgentRule, this.patchAgentRule,
                this.deleteAgentRule, this.acceptAgentRule, this.rejectAgentRule);
    }

    @Test
    void givenAgentIdAndFilters_whenGetAgentRules_thenReturnRulesResponseDto() {
        //given
        final UUID agentId = UUID.randomUUID();
        final AgentRuleStatusDTO status = mock(AgentRuleStatusDTO.class);
        final GetAgentRulesQuery query = mock(GetAgentRulesQuery.class);
        final AgentRulesResponse rules = mock(AgentRulesResponse.class);
        final AgentRulesResponseDTO response = mock(AgentRulesResponseDTO.class);
        when(this.agentRuleApiMapper.asGetAgentRulesQuery(status, null)).thenReturn(query);
        when(this.getAgentRules.execute(agentId, query)).thenReturn(rules);
        when(this.agentRuleApiMapper.asAgentRulesResponseDto(rules)).thenReturn(response);

        //when
        final ResponseEntity<AgentRulesResponseDTO> actual = this.agentRuleController.getAgentRules(agentId, status, null);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(response));
        verify(this.agentRuleApiMapper).asGetAgentRulesQuery(status, null);
        verify(this.getAgentRules).execute(agentId, query);
        verify(this.agentRuleApiMapper).asAgentRulesResponseDto(rules);
    }

    @Test
    void givenCreateRequest_whenCreateAgentRule_thenReturnCreatedRuleDto() {
        //given
        final UUID agentId = UUID.randomUUID();
        final CreateAgentRuleRequestDTO requestDto = mock(CreateAgentRuleRequestDTO.class);
        final CreateAgentRuleRequest request = mock(CreateAgentRuleRequest.class);
        final AgentRule rule = mock(AgentRule.class);
        final AgentRuleDTO response = mock(AgentRuleDTO.class);
        when(this.agentRuleApiMapper.asCreateAgentRuleRequest(requestDto)).thenReturn(request);
        when(this.createAgentRule.execute(agentId, request)).thenReturn(rule);
        when(this.agentRuleApiMapper.asAgentRuleDto(rule)).thenReturn(response);

        //when
        final ResponseEntity<AgentRuleDTO> actual = this.agentRuleController.createAgentRule(agentId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(response));
        verify(this.agentRuleApiMapper).asCreateAgentRuleRequest(requestDto);
        verify(this.createAgentRule).execute(agentId, request);
        verify(this.agentRuleApiMapper).asAgentRuleDto(rule);
    }

    @Test
    void givenPatchRequest_whenPatchAgentRule_thenReturnPatchedRuleDto() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final PatchAgentRuleRequestDTO requestDto = mock(PatchAgentRuleRequestDTO.class);
        final PatchAgentRuleRequest request = mock(PatchAgentRuleRequest.class);
        final AgentRule rule = mock(AgentRule.class);
        final AgentRuleDTO response = mock(AgentRuleDTO.class);
        when(this.agentRuleApiMapper.asPatchAgentRuleRequest(requestDto)).thenReturn(request);
        when(this.patchAgentRule.execute(agentId, ruleId, request)).thenReturn(rule);
        when(this.agentRuleApiMapper.asAgentRuleDto(rule)).thenReturn(response);

        //when
        final ResponseEntity<AgentRuleDTO> actual = this.agentRuleController.patchAgentRule(agentId, ruleId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(response));
        verify(this.agentRuleApiMapper).asPatchAgentRuleRequest(requestDto);
        verify(this.patchAgentRule).execute(agentId, ruleId, request);
        verify(this.agentRuleApiMapper).asAgentRuleDto(rule);
    }

    @Test
    void givenRuleId_whenDeleteAgentRule_thenReturnDeleteResponseDto() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final DeleteAgentRuleResponse deleteResponse = mock(DeleteAgentRuleResponse.class);
        final DeleteAgentRuleResponseDTO response = mock(DeleteAgentRuleResponseDTO.class);
        when(this.deleteAgentRule.execute(agentId, ruleId)).thenReturn(deleteResponse);
        when(this.agentRuleApiMapper.asDeleteAgentRuleResponseDto(deleteResponse)).thenReturn(response);

        //when
        final ResponseEntity<DeleteAgentRuleResponseDTO> actual = this.agentRuleController.deleteAgentRule(agentId, ruleId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(response));
        verify(this.deleteAgentRule).execute(agentId, ruleId);
        verify(this.agentRuleApiMapper).asDeleteAgentRuleResponseDto(deleteResponse);
    }

    @Test
    void givenAcceptRequest_whenAcceptAgentRule_thenReturnAcceptedRuleDto() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final AcceptAgentRuleRequestDTO requestDto = mock(AcceptAgentRuleRequestDTO.class);
        final AcceptAgentRuleRequest request = mock(AcceptAgentRuleRequest.class);
        final AgentRule rule = mock(AgentRule.class);
        final AgentRuleDTO response = mock(AgentRuleDTO.class);
        when(this.agentRuleApiMapper.asAcceptAgentRuleRequest(requestDto)).thenReturn(request);
        when(this.acceptAgentRule.execute(agentId, ruleId, request)).thenReturn(rule);
        when(this.agentRuleApiMapper.asAgentRuleDto(rule)).thenReturn(response);

        //when
        final ResponseEntity<AgentRuleDTO> actual = this.agentRuleController.acceptAgentRule(agentId, ruleId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(response));
        verify(this.agentRuleApiMapper).asAcceptAgentRuleRequest(requestDto);
        verify(this.acceptAgentRule).execute(agentId, ruleId, request);
        verify(this.agentRuleApiMapper).asAgentRuleDto(rule);
    }

    @Test
    void givenAgentAndRuleId_whenRejectAgentRule_thenReturnRejectedRuleDto() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final AgentRule rule = mock(AgentRule.class);
        final AgentRuleDTO response = mock(AgentRuleDTO.class);
        when(this.rejectAgentRule.execute(agentId, ruleId)).thenReturn(rule);
        when(this.agentRuleApiMapper.asAgentRuleDto(rule)).thenReturn(response);

        //when
        final ResponseEntity<AgentRuleDTO> actual = this.agentRuleController.rejectAgentRule(agentId, ruleId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(response));
        verify(this.rejectAgentRule).execute(agentId, ruleId);
        verify(this.agentRuleApiMapper).asAgentRuleDto(rule);
    }
}
