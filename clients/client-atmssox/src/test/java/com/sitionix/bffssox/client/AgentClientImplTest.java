package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.ChatAgentClientMapper;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentClientImplTest {

    private com.sitionix.bffssox.client.AgentClient agentClient;

    @Mock
    private AgentApi agentApi;

    @Mock
    private CreateAgentClientMapper createAgentClientMapper;

    @Mock
    private AgentClientMapper agentClientMapper;

    @Mock
    private PatchAgentClientMapper patchAgentClientMapper;

    @Mock
    private ChatAgentClientMapper chatAgentClientMapper;

    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentClient = new AgentClientImpl(
                this.agentApi,
                this.createAgentClientMapper,
                this.agentClientMapper,
                this.patchAgentClientMapper,
                this.chatAgentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.agentApi,
                this.createAgentClientMapper,
                this.agentClientMapper,
                this.patchAgentClientMapper,
                this.chatAgentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @Test
    void givenCreateAgentRequest_whenCreateAgent_thenReturnAgent() {
        //given
        final CreateAgentRequest request = mock(CreateAgentRequest.class);
        final CreateAgentRequestDTO requestDTO = mock(CreateAgentRequestDTO.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent response = mock(Agent.class);

        when(this.createAgentClientMapper.asCreateAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.createAgent(requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(response);

        //when
        final Agent actual = this.agentClient.createAgent(request);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.createAgentClientMapper).asCreateAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).createAgent(requestDTO);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenGetAgentsRequest_whenGetAgents_thenReturnAgentsResponse() {
        //given
        final AgentsResponseDTO responseDTO = mock(AgentsResponseDTO.class);
        final AgentsResponse response = mock(AgentsResponse.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentsResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgents()).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentsResponse(responseDTO)).thenReturn(response);

        //when
        final AgentsResponse actual = this.agentClient.getAgents();

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgents();
        verify(this.agentClientMapper).asAgentsResponse(responseDTO);
    }

    @Test
    void givenAgentId_whenGetAgent_thenReturnAgent() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent response = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgent(agentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(response);

        //when
        final Agent actual = this.agentClient.getAgent(agentId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgent(agentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenPatchAgentRequest_whenPatchAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final PatchAgentRequest request = mock(PatchAgentRequest.class);
        final PatchAgentRequestDTO requestDTO = mock(PatchAgentRequestDTO.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.patchAgentClientMapper.asPatchAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.patchAgent(givenAgentId, requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.patchAgent(givenAgentId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.patchAgentClientMapper).asPatchAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).patchAgent(givenAgentId, requestDTO);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenActivateAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("88e2673a-273a-4cf4-a94f-96f4df311ea9");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.activateAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.activateAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).activateAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenArchiveAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("f8f58985-8fa8-4412-8f1e-7955f0f8f85e");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.archiveAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.archiveAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).archiveAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenRestoreAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("c1db6b3a-2ee2-4f59-889b-ec3e8e343973");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.restoreAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.restoreAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).restoreAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenDeleteAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("78ad5fab-af5a-4667-a150-33970a9f72b0");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.deleteAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.deleteAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).deleteAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }
}
