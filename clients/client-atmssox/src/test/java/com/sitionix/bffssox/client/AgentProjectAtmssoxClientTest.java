package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentProjectApi;
import com.app_afesox.atmssox.client.dto.AddAgentToProjectRequestDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectFlowPaletteResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectFlowResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentProjectRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentProjectRequestDTO;
import com.app_afesox.atmssox.client.dto.ProjectAgentResponseDTO;
import com.app_afesox.atmssox.client.dto.ProjectAgentsResponseDTO;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectFlow;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentProjectClientMapper;
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
class AgentProjectAtmssoxClientTest {

    private AgentProjectAtmssoxClient agentProjectAtmssoxClient;

    @Mock
    private AgentProjectApi agentProjectApi;
    @Mock
    private CreateAgentProjectClientMapper createAgentProjectClientMapper;
    @Mock
    private PatchAgentProjectClientMapper patchAgentProjectClientMapper;
    @Mock
    private AgentClientMapper agentClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentProjectAtmssoxClient = new AgentProjectAtmssoxClient(
                this.agentProjectApi,
                this.createAgentProjectClientMapper,
                this.patchAgentProjectClientMapper,
                this.agentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentProjectApi, this.createAgentProjectClientMapper, this.patchAgentProjectClientMapper,
                this.agentClientMapper, this.atmssoxClientCallExecutor);
    }

    @Test
    void givenCreateProjectRequest_whenCreateAgentProject_thenReturnMappedProject() {
        //given
        final CreateAgentProjectRequest request = mock(CreateAgentProjectRequest.class);
        final CreateAgentProjectRequestDTO requestDTO = mock(CreateAgentProjectRequestDTO.class);
        final AgentProjectDTO responseDTO = mock(AgentProjectDTO.class);
        final AgentProject expected = mock(AgentProject.class);
        when(this.createAgentProjectClientMapper.asCreateAgentProjectRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentProjectDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.createAgentProject(requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentProject(responseDTO)).thenReturn(expected);

        //when
        final AgentProject actual = this.agentProjectAtmssoxClient.createAgentProject(request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.createAgentProjectClientMapper).asCreateAgentProjectRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).createAgentProject(requestDTO);
        verify(this.agentClientMapper).asAgentProject(responseDTO);
    }

    @Test
    void givenPatchProjectRequest_whenPatchAgentProject_thenReturnMappedProject() {
        //given
        final UUID projectId = UUID.randomUUID();
        final PatchAgentProjectRequest request = mock(PatchAgentProjectRequest.class);
        final PatchAgentProjectRequestDTO requestDTO = mock(PatchAgentProjectRequestDTO.class);
        final AgentProjectDTO responseDTO = mock(AgentProjectDTO.class);
        final AgentProject expected = mock(AgentProject.class);
        when(this.patchAgentProjectClientMapper.asPatchAgentProjectRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentProjectDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.patchAgentProject(projectId, requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentProject(responseDTO)).thenReturn(expected);

        //when
        final AgentProject actual = this.agentProjectAtmssoxClient.patchAgentProject(projectId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.patchAgentProjectClientMapper).asPatchAgentProjectRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).patchAgentProject(projectId, requestDTO);
        verify(this.agentClientMapper).asAgentProject(responseDTO);
    }

    @Test
    void givenProjectId_whenDeleteAgentProject_thenCallDeleteApi() {
        //given
        final UUID projectId = UUID.randomUUID();
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<Void>) invocation.getArgument(0)).get());

        //when
        this.agentProjectAtmssoxClient.deleteAgentProject(projectId);

        //then
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).deleteAgentProject(projectId);
    }

    @Test
    void givenProjectId_whenGetProjectAgents_thenReturnMappedResponse() {
        //given
        final UUID projectId = UUID.randomUUID();
        final ProjectAgentsResponseDTO responseDTO = mock(ProjectAgentsResponseDTO.class);
        final ProjectAgentsResponse expected = mock(ProjectAgentsResponse.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<ProjectAgentsResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.listAgentProjectAgents(projectId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asProjectAgentsResponse(responseDTO)).thenReturn(expected);

        //when
        final ProjectAgentsResponse actual = this.agentProjectAtmssoxClient.getProjectAgents(projectId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).listAgentProjectAgents(projectId);
        verify(this.agentClientMapper).asProjectAgentsResponse(responseDTO);
    }

    @Test
    void givenRequest_whenAddAgentToProject_thenReturnMappedResponse() {
        //given
        final UUID projectId = UUID.randomUUID();
        final AddAgentToProjectRequest request = mock(AddAgentToProjectRequest.class);
        final AddAgentToProjectRequestDTO requestDTO = mock(AddAgentToProjectRequestDTO.class);
        final ProjectAgentResponseDTO responseDTO = mock(ProjectAgentResponseDTO.class);
        final ProjectAgent expected = mock(ProjectAgent.class);
        when(this.agentClientMapper.asAddAgentToProjectRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<ProjectAgentResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.addAgentToProject(projectId, requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asProjectAgent(responseDTO)).thenReturn(expected);

        //when
        final ProjectAgent actual = this.agentProjectAtmssoxClient.addAgentToProject(projectId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClientMapper).asAddAgentToProjectRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).addAgentToProject(projectId, requestDTO);
        verify(this.agentClientMapper).asProjectAgent(responseDTO);
    }

    @Test
    void givenProjectIdAndAgentId_whenRemoveAgentFromProject_thenCallDeleteApi() {
        //given
        final UUID projectId = UUID.randomUUID();
        final UUID agentId = UUID.randomUUID();
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<Void>) invocation.getArgument(0)).get());

        //when
        this.agentProjectAtmssoxClient.removeAgentFromProject(projectId, agentId);

        //then
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).removeAgentFromProject(projectId, agentId);
    }

    @Test
    void givenProjectId_whenGetAgentProjectFlow_thenReturnMappedFlow() {
        //given
        final UUID projectId = UUID.randomUUID();
        final AgentProjectFlowResponseDTO responseDTO = mock(AgentProjectFlowResponseDTO.class);
        final AgentProjectFlow expected = mock(AgentProjectFlow.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentProjectFlowResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.getAgentProjectFlow(projectId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentProjectFlow(responseDTO)).thenReturn(expected);

        //when
        final AgentProjectFlow actual = this.agentProjectAtmssoxClient.getAgentProjectFlow(projectId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).getAgentProjectFlow(projectId);
        verify(this.agentClientMapper).asAgentProjectFlow(responseDTO);
    }

    @Test
    void givenProjectId_whenGetAgentProjectFlowPalette_thenReturnMappedPalette() {
        //given
        final UUID projectId = UUID.randomUUID();
        final AgentProjectFlowPaletteResponseDTO responseDTO = mock(AgentProjectFlowPaletteResponseDTO.class);
        final AgentProjectFlowPaletteResponse expected = mock(AgentProjectFlowPaletteResponse.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentProjectFlowPaletteResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.getAgentProjectFlowPalette(projectId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentProjectFlowPaletteResponse(responseDTO)).thenReturn(expected);

        //when
        final AgentProjectFlowPaletteResponse actual = this.agentProjectAtmssoxClient.getAgentProjectFlowPalette(projectId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).getAgentProjectFlowPalette(projectId);
        verify(this.agentClientMapper).asAgentProjectFlowPaletteResponse(responseDTO);
    }
}
