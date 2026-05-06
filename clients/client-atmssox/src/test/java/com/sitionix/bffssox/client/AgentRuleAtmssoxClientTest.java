package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentRuleApi;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
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
}
