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
import com.sitionix.bffssox.usecase.GetAgent;
import com.sitionix.bffssox.usecase.GetAgents;
import com.sitionix.bffssox.usecase.PatchAgent;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.openapitools.jackson.nullable.JsonNullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentControllerTest {

    private AgentController agentController;

    @Mock
    private CreateAgentApiMapper createAgentApiMapper;

    @Mock
    private AgentApiMapper agentApiMapper;

    @Mock
    private CreateAgent createAgent;

    @Mock
    private PatchAgentApiMapper patchAgentApiMapper;

    @Mock
    private PatchAgent patchAgent;

    @Mock
    private GetAgents getAgents;

    @Mock
    private GetAgent getAgent;

    @Mock
    private ActivateAgent activateAgent;

    @Mock
    private ArchiveAgent archiveAgent;

    @BeforeEach
    void setUp() {
        this.agentController = new AgentController(
                this.createAgentApiMapper,
                this.agentApiMapper,
                this.createAgent,
                this.patchAgentApiMapper,
                this.patchAgent,
                this.getAgents,
                this.getAgent,
                this.activateAgent,
                this.archiveAgent
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.createAgentApiMapper,
                this.agentApiMapper,
                this.createAgent,
                this.patchAgentApiMapper,
                this.patchAgent,
                this.getAgents,
                this.getAgent,
                this.activateAgent,
                this.archiveAgent
        );
    }

    @Test
    void givenCreateAgentRequestDto_whenCreateAgent_thenReturnCreatedResponse() {
        //given
        final CreateAgentRequestDTO requestDTO = mock(CreateAgentRequestDTO.class);
        final CreateAgentRequest request = mock(CreateAgentRequest.class);
        final Agent response = mock(Agent.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);

        when(this.createAgentApiMapper.asCreateAgentRequest(requestDTO)).thenReturn(request);
        when(this.createAgent.execute(request)).thenReturn(response);
        when(this.agentApiMapper.asAgentDto(response)).thenReturn(responseDTO);

        //when
        final ResponseEntity<AgentDTO> actual = this.agentController.createAgent(requestDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(responseDTO));
        verify(this.createAgentApiMapper).asCreateAgentRequest(requestDTO);
        verify(this.createAgent).execute(request);
        verify(this.agentApiMapper).asAgentDto(response);
    }

    @Test
    void givenGetAgentsRequest_whenGetAgents_thenReturnOkResponse() {
        //given
        final AgentsResponse response = mock(AgentsResponse.class);
        final AgentsResponseDTO responseDTO = mock(AgentsResponseDTO.class);

        when(this.getAgents.execute()).thenReturn(response);
        when(this.agentApiMapper.asAgentsResponseDto(response)).thenReturn(responseDTO);

        //when
        final ResponseEntity<AgentsResponseDTO> actual = this.agentController.getAgents();

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.getAgents).execute();
        verify(this.agentApiMapper).asAgentsResponseDto(response);
    }

    @Test
    void givenAgentId_whenGetAgent_thenReturnOkResponse() {
        //given
        final UUID agentId = UUID.fromString("ebac37f0-90a2-4f6b-ab99-73f6ac5cf675");
        final Agent response = mock(Agent.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);

        when(this.getAgent.execute(agentId)).thenReturn(response);
        when(this.agentApiMapper.asAgentDto(response)).thenReturn(responseDTO);

        //when
        final ResponseEntity<AgentDTO> actual = this.agentController.getAgent(agentId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.getAgent).execute(agentId);
        verify(this.agentApiMapper).asAgentDto(response);
    }

    @Test
    void givenPatchAgentRequestDto_whenPatchAgent_thenReturnOkResponse() {
        //given
        final UUID givenAgentId = UUID.fromString("ebac37f0-90a2-4f6b-ab99-73f6ac5cf675");
        final PatchAgentRequestDTO givenRequestDTO = PatchAgentRequestDTO.builder()
                .name("Updated Architecture Reviewer")
                .build();
        final PatchAgentRequest request = mock(PatchAgentRequest.class);
        final Agent response = mock(Agent.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);

        when(this.patchAgentApiMapper.asPatchAgentRequest(givenRequestDTO)).thenReturn(request);
        when(this.patchAgent.execute(givenAgentId, request)).thenReturn(response);
        when(this.agentApiMapper.asAgentDto(response)).thenReturn(responseDTO);

        //when
        final ResponseEntity<AgentDTO> actual = this.agentController.patchAgent(givenAgentId, givenRequestDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.patchAgentApiMapper).asPatchAgentRequest(givenRequestDTO);
        verify(this.patchAgent).execute(givenAgentId, request);
        verify(this.agentApiMapper).asAgentDto(response);
    }

    @Test
    void givenEmptyPatchAgentRequestDto_whenPatchAgent_thenThrowIllegalArgumentException() {
        //given
        final UUID givenAgentId = UUID.fromString("ebac37f0-90a2-4f6b-ab99-73f6ac5cf675");
        final PatchAgentRequestDTO givenRequestDTO = PatchAgentRequestDTO.builder()
                .name(null)
                .description(JsonNullable.undefined())
                .instruction(JsonNullable.undefined())
                .build();

        //when then
        assertThatThrownBy(() -> this.agentController.patchAgent(givenAgentId, givenRequestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Patch payload cannot be empty");
    }

    @Test
    void givenAgentId_whenActivateAgent_thenReturnOkResponse() {
        //given
        final UUID givenAgentId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        final Agent response = mock(Agent.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);

        when(this.activateAgent.execute(givenAgentId)).thenReturn(response);
        when(this.agentApiMapper.asAgentDto(response)).thenReturn(responseDTO);

        //when
        final ResponseEntity<AgentDTO> actual = this.agentController.activateAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.activateAgent).execute(givenAgentId);
        verify(this.agentApiMapper).asAgentDto(response);
    }

    @Test
    void givenAgentId_whenArchiveAgent_thenReturnOkResponse() {
        //given
        final UUID givenAgentId = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        final Agent response = mock(Agent.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);

        when(this.archiveAgent.execute(givenAgentId)).thenReturn(response);
        when(this.agentApiMapper.asAgentDto(response)).thenReturn(responseDTO);

        //when
        final ResponseEntity<AgentDTO> actual = this.agentController.archiveAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.archiveAgent).execute(givenAgentId);
        verify(this.agentApiMapper).asAgentDto(response);
    }
}
