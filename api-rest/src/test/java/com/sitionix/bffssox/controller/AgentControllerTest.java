package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import com.sitionix.bffssox.mapper.AgentApiMapper;
import com.sitionix.bffssox.mapper.CreateAgentApiMapper;
import com.sitionix.bffssox.mapper.PatchAgentApiMapper;
import com.sitionix.bffssox.usecase.ActivateAgent;
import com.sitionix.bffssox.usecase.ArchiveAgent;
import com.sitionix.bffssox.usecase.CreateAgent;
import com.sitionix.bffssox.usecase.DeleteAgent;
import com.sitionix.bffssox.usecase.GetAgent;
import com.sitionix.bffssox.usecase.GetAgents;
import com.sitionix.bffssox.usecase.PatchAgent;
import com.sitionix.bffssox.usecase.RestoreAgent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentControllerTest {

    private AgentController agentController;

    @Mock private CreateAgentApiMapper createAgentApiMapper;
    @Mock private AgentApiMapper agentApiMapper;
    @Mock private CreateAgent createAgent;
    @Mock private PatchAgentApiMapper patchAgentApiMapper;
    @Mock private PatchAgent patchAgent;
    @Mock private GetAgents getAgents;
    @Mock private GetAgent getAgent;
    @Mock private ActivateAgent activateAgent;
    @Mock private ArchiveAgent archiveAgent;
    @Mock private RestoreAgent restoreAgent;
    @Mock private DeleteAgent deleteAgent;

    @BeforeEach
    void setUp() {
        this.agentController = new AgentController(this.createAgentApiMapper, this.agentApiMapper, this.createAgent, this.patchAgentApiMapper,
                this.patchAgent, this.getAgents, this.getAgent, this.activateAgent, this.archiveAgent, this.restoreAgent, this.deleteAgent);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.createAgentApiMapper, this.agentApiMapper, this.createAgent, this.patchAgentApiMapper, this.patchAgent,
                this.getAgents, this.getAgent, this.activateAgent, this.archiveAgent, this.restoreAgent, this.deleteAgent);
    }

    @Test
    void givenCreateAgentRequestDto_whenCreateAgent_thenReturnCreatedResponse() {
        //given
        final CreateAgentRequestDTO requestDto = mock(CreateAgentRequestDTO.class);
        final CreateAgentRequest request = mock(CreateAgentRequest.class);
        final Agent response = mock(Agent.class);
        final AgentDTO responseDto = mock(AgentDTO.class);
        when(this.createAgentApiMapper.asCreateAgentRequest(requestDto)).thenReturn(request);
        when(this.createAgent.execute(request)).thenReturn(response);
        when(this.agentApiMapper.asAgentDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentDTO> actual = this.agentController.createAgent(requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(responseDto));
        verify(this.createAgentApiMapper).asCreateAgentRequest(requestDto);
        verify(this.createAgent).execute(request);
        verify(this.agentApiMapper).asAgentDto(response);
    }

    @Test
    void givenNoInput_whenGetAgents_thenReturnAgentsResponseDto() {
        //given
        final AgentsResponse response = mock(AgentsResponse.class);
        final AgentsResponseDTO responseDto = mock(AgentsResponseDTO.class);
        when(this.getAgents.execute()).thenReturn(response);
        when(this.agentApiMapper.asAgentsResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentsResponseDTO> actual = this.agentController.getAgents();

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgents).execute();
        verify(this.agentApiMapper).asAgentsResponseDto(response);
    }

    @Test
    void givenAgentId_whenGetActivateArchiveRestoreDeleteAgent_thenReturnAgentDtos() {
        //given
        final UUID agentId = UUID.randomUUID();
        final Agent agent = mock(Agent.class);
        final AgentDTO dto = mock(AgentDTO.class);
        when(this.getAgent.execute(agentId)).thenReturn(agent);
        when(this.activateAgent.execute(agentId)).thenReturn(agent);
        when(this.archiveAgent.execute(agentId)).thenReturn(agent);
        when(this.restoreAgent.execute(agentId)).thenReturn(agent);
        when(this.deleteAgent.execute(agentId)).thenReturn(agent);
        when(this.agentApiMapper.asAgentDto(agent)).thenReturn(dto);

        //when
        final ResponseEntity<AgentDTO> getResponse = this.agentController.getAgent(agentId);
        final ResponseEntity<AgentDTO> activateResponse = this.agentController.activateAgent(agentId);
        final ResponseEntity<AgentDTO> archiveResponse = this.agentController.archiveAgent(agentId);
        final ResponseEntity<AgentDTO> restoreResponse = this.agentController.restoreAgent(agentId);
        final ResponseEntity<AgentDTO> deleteResponse = this.agentController.deleteAgent(agentId);

        //then
        assertThat(getResponse).isEqualTo(ResponseEntity.ok(dto));
        assertThat(activateResponse).isEqualTo(ResponseEntity.ok(dto));
        assertThat(archiveResponse).isEqualTo(ResponseEntity.ok(dto));
        assertThat(restoreResponse).isEqualTo(ResponseEntity.ok(dto));
        assertThat(deleteResponse).isEqualTo(ResponseEntity.ok(dto));
        verify(this.getAgent).execute(agentId);
        verify(this.activateAgent).execute(agentId);
        verify(this.archiveAgent).execute(agentId);
        verify(this.restoreAgent).execute(agentId);
        verify(this.deleteAgent).execute(agentId);
        verify(this.agentApiMapper, times(5)).asAgentDto(agent);
    }

    @Test
    void givenPatchPayloadWithValues_whenPatchAgent_thenReturnAgentDto() {
        //given
        final UUID agentId = UUID.randomUUID();
        final PatchAgentRequestDTO requestDto = mock(PatchAgentRequestDTO.class);
        final PatchAgentRequest request = mock(PatchAgentRequest.class);
        final Agent response = mock(Agent.class);
        final AgentDTO responseDto = mock(AgentDTO.class);
        when(requestDto.getName()).thenReturn("name");
        when(this.patchAgentApiMapper.asPatchAgentRequest(requestDto)).thenReturn(request);
        when(this.patchAgent.execute(agentId, request)).thenReturn(response);
        when(this.agentApiMapper.asAgentDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentDTO> actual = this.agentController.patchAgent(agentId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.patchAgentApiMapper).asPatchAgentRequest(requestDto);
        verify(this.patchAgent).execute(agentId, request);
        verify(this.agentApiMapper).asAgentDto(response);
    }

    @Test
    void givenEmptyPatchPayload_whenPatchAgent_thenThrowIllegalArgumentException() {
        //given
        final UUID agentId = UUID.randomUUID();
        final PatchAgentRequestDTO requestDto = mock(PatchAgentRequestDTO.class);
        when(requestDto.getName()).thenReturn(null);
        when(requestDto.getDescription()).thenReturn(null);
        when(requestDto.getInstruction()).thenReturn(null);

        //when/then
        assertThatThrownBy(() -> this.agentController.patchAgent(agentId, requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Patch payload cannot be empty");
    }
}
