package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.CreateAgentRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
}
