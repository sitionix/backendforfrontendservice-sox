package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentRuleApi;
import com.app_afesox.atmssox.client.dto.AcceptAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.AgentRulesResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
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
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentRuleAtmssoxClientTest {

    private AgentRuleAtmssoxClient agentRuleAtmssoxClient;

    @Mock
    private AgentRuleApi agentRuleApi;
    @Mock
    private AgentRuleClientMapper agentRuleClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentRuleAtmssoxClient = new AgentRuleAtmssoxClient(this.agentRuleApi, this.agentRuleClientMapper, this.atmssoxClientCallExecutor);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentRuleApi, this.agentRuleClientMapper, this.atmssoxClientCallExecutor);
    }

    @Test
    void givenCreateRuleRequest_whenCreateAgentRule_thenReturnMappedRule() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final CreateAgentRuleRequest request = mock(CreateAgentRuleRequest.class);
        final CreateAgentRuleRequestDTO requestDTO = mock(CreateAgentRuleRequestDTO.class);
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);
        when(this.agentRuleClientMapper.asCreateAgentRuleRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentRuleDTO>) invocation.getArgument(0)).get());
        when(this.agentRuleApi.createAgentRule(agentId, requestDTO)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentRuleAtmssoxClient.createAgentRule(agentId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentRuleClientMapper).asCreateAgentRuleRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentRuleApi).createAgentRule(agentId, requestDTO);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenAgentAndQuery_whenGetAgentRules_thenReturnMappedResponse() {
        //given
        final UUID agentId = UUID.randomUUID();
        final GetAgentRulesQuery query = new GetAgentRulesQuery("ACTIVE", "USER");
        final AgentRulesResponseDTO responseDTO = mock(AgentRulesResponseDTO.class);
        final AgentRulesResponse expected = mock(AgentRulesResponse.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentRulesResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentRuleApi.getAgentRules(agentId, com.app_afesox.atmssox.client.dto.AgentRuleStatusDTO.ACTIVE,
                com.app_afesox.atmssox.client.dto.AgentRuleAuthorTypeDTO.USER)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRulesResponse(responseDTO)).thenReturn(expected);

        //when
        final AgentRulesResponse actual = this.agentRuleAtmssoxClient.getAgentRules(agentId, query);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentRuleApi).getAgentRules(agentId, com.app_afesox.atmssox.client.dto.AgentRuleStatusDTO.ACTIVE,
                com.app_afesox.atmssox.client.dto.AgentRuleAuthorTypeDTO.USER);
        verify(this.agentRuleClientMapper).asAgentRulesResponse(responseDTO);
    }

    @Test
    void givenPatchRequest_whenPatchAgentRule_thenReturnMappedRule() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final PatchAgentRuleRequest request = mock(PatchAgentRuleRequest.class);
        final PatchAgentRuleRequestDTO requestDTO = mock(PatchAgentRuleRequestDTO.class);
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);
        when(this.agentRuleClientMapper.asPatchAgentRuleRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentRuleDTO>) invocation.getArgument(0)).get());
        when(this.agentRuleApi.patchAgentRule(agentId, ruleId, requestDTO)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentRuleAtmssoxClient.patchAgentRule(agentId, ruleId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentRuleClientMapper).asPatchAgentRuleRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentRuleApi).patchAgentRule(agentId, ruleId, requestDTO);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenAcceptRequest_whenAcceptAgentRule_thenReturnMappedRule() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final AcceptAgentRuleRequest request = mock(AcceptAgentRuleRequest.class);
        final AcceptAgentRuleRequestDTO requestDTO = mock(AcceptAgentRuleRequestDTO.class);
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);
        when(this.agentRuleClientMapper.asAcceptAgentRuleRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentRuleDTO>) invocation.getArgument(0)).get());
        when(this.agentRuleApi.acceptAgentRule(agentId, ruleId, requestDTO)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentRuleAtmssoxClient.acceptAgentRule(agentId, ruleId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentRuleClientMapper).asAcceptAgentRuleRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentRuleApi).acceptAgentRule(agentId, ruleId, requestDTO);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenRuleId_whenRejectAgentRule_thenReturnMappedRule() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentRuleDTO>) invocation.getArgument(0)).get());
        when(this.agentRuleApi.rejectAgentRule(agentId, ruleId)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentRuleAtmssoxClient.rejectAgentRule(agentId, ruleId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentRuleApi).rejectAgentRule(agentId, ruleId);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenRuleId_whenDeleteAgentRule_thenReturnMappedResponse() {
        //given
        final UUID agentId = UUID.randomUUID();
        final UUID ruleId = UUID.randomUUID();
        final DeleteAgentRuleResponseDTO responseDTO = mock(DeleteAgentRuleResponseDTO.class);
        final DeleteAgentRuleResponse expected = mock(DeleteAgentRuleResponse.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<DeleteAgentRuleResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentRuleApi.deleteAgentRule(agentId, ruleId)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asDeleteAgentRuleResponse(responseDTO)).thenReturn(expected);

        //when
        final DeleteAgentRuleResponse actual = this.agentRuleAtmssoxClient.deleteAgentRule(agentId, ruleId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentRuleApi).deleteAgentRule(agentId, ruleId);
        verify(this.agentRuleClientMapper).asDeleteAgentRuleResponse(responseDTO);
    }
}
