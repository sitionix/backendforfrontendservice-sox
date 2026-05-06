package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentClientMapper;
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
class AgentAtmssoxClientTest {

    private AgentAtmssoxClient agentAtmssoxClient;

    @Mock
    private AgentApi agentApi;
    @Mock
    private CreateAgentClientMapper createAgentClientMapper;
    @Mock
    private PatchAgentClientMapper patchAgentClientMapper;
    @Mock
    private AgentClientMapper agentClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentAtmssoxClient = new AgentAtmssoxClient(
                this.agentApi,
                this.createAgentClientMapper,
                this.patchAgentClientMapper,
                this.agentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentApi, this.createAgentClientMapper, this.patchAgentClientMapper, this.agentClientMapper,
                this.atmssoxClientCallExecutor);
    }

    @Test
    void givenCreateAgentRequest_whenCreateAgent_thenReturnMappedAgent() {
        //given
        final CreateAgentRequest request = mock(CreateAgentRequest.class);
        final CreateAgentRequestDTO requestDTO = mock(CreateAgentRequestDTO.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);
        when(this.createAgentClientMapper.asCreateAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentDTO>) invocation.getArgument(0)).get());
        when(this.agentApi.createAgent(requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentAtmssoxClient.createAgent(request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.createAgentClientMapper).asCreateAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).createAgent(requestDTO);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentIdAndPatchRequest_whenPatchAgent_thenReturnMappedAgent() {
        //given
        final java.util.UUID agentId = java.util.UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final com.sitionix.bffssox.domain.PatchAgentRequest request = mock(com.sitionix.bffssox.domain.PatchAgentRequest.class);
        final com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO requestDTO = mock(com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);
        when(this.patchAgentClientMapper.asPatchAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentDTO>) invocation.getArgument(0)).get());
        when(this.agentApi.patchAgent(agentId, requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentAtmssoxClient.patchAgent(agentId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.patchAgentClientMapper).asPatchAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).patchAgent(agentId, requestDTO);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }
}
