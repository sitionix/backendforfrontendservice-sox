package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentClientMapper;
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
import static org.mockito.Mockito.times;
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

    @Test
    void givenNoInput_whenGetAgents_thenReturnMappedAgentsResponse() {
        //given
        final AgentsResponseDTO responseDTO = mock(AgentsResponseDTO.class);
        final AgentsResponse expected = mock(AgentsResponse.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentsResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentApi.getAgents()).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentsResponse(responseDTO)).thenReturn(expected);

        //when
        final AgentsResponse actual = this.agentAtmssoxClient.getAgents();

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgents();
        verify(this.agentClientMapper).asAgentsResponse(responseDTO);
    }

    @Test
    void givenAgentId_whenGetActivateArchiveRestoreDelete_thenReturnMappedAgent() {
        //given
        final UUID agentId = UUID.randomUUID();
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentDTO>) invocation.getArgument(0)).get());
        when(this.agentApi.getAgent(agentId)).thenReturn(responseDTO);
        when(this.agentApi.activateAgent(agentId)).thenReturn(responseDTO);
        when(this.agentApi.archiveAgent(agentId)).thenReturn(responseDTO);
        when(this.agentApi.restoreAgent(agentId)).thenReturn(responseDTO);
        when(this.agentApi.deleteAgent(agentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent getResponse = this.agentAtmssoxClient.getAgent(agentId);
        final Agent activateResponse = this.agentAtmssoxClient.activateAgent(agentId);
        final Agent archiveResponse = this.agentAtmssoxClient.archiveAgent(agentId);
        final Agent restoreResponse = this.agentAtmssoxClient.restoreAgent(agentId);
        final Agent deleteResponse = this.agentAtmssoxClient.deleteAgent(agentId);

        //then
        assertThat(getResponse).isEqualTo(expected);
        assertThat(activateResponse).isEqualTo(expected);
        assertThat(archiveResponse).isEqualTo(expected);
        assertThat(restoreResponse).isEqualTo(expected);
        assertThat(deleteResponse).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor, times(5)).execute(any());
        verify(this.agentApi).getAgent(agentId);
        verify(this.agentApi).activateAgent(agentId);
        verify(this.agentApi).archiveAgent(agentId);
        verify(this.agentApi).restoreAgent(agentId);
        verify(this.agentApi).deleteAgent(agentId);
        verify(this.agentClientMapper, times(5)).asAgent(responseDTO);
    }
}
