package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.bffssox.api_first.dto.AddAgentToProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentsResponseDTO;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import com.sitionix.bffssox.mapper.AgentApiMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectApiMapper;
import com.sitionix.bffssox.mapper.PatchAgentProjectApiMapper;
import com.sitionix.bffssox.usecase.AddAgentToProject;
import com.sitionix.bffssox.usecase.CreateAgentProject;
import com.sitionix.bffssox.usecase.DeleteAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProjects;
import com.sitionix.bffssox.usecase.GetProjectAgents;
import com.sitionix.bffssox.usecase.PatchAgentProject;
import com.sitionix.bffssox.usecase.RemoveAgentFromProject;
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
class AgentProjectControllerTest {

    private AgentProjectController agentProjectController;

    @Mock private CreateAgentProjectApiMapper createAgentProjectApiMapper;
    @Mock private AgentApiMapper agentApiMapper;
    @Mock private CreateAgentProject createAgentProject;
    @Mock private GetAgentProjects getAgentProjects;
    @Mock private GetAgentProject getAgentProject;
    @Mock private PatchAgentProjectApiMapper patchAgentProjectApiMapper;
    @Mock private PatchAgentProject patchAgentProject;
    @Mock private DeleteAgentProject deleteAgentProject;
    @Mock private GetProjectAgents getProjectAgents;
    @Mock private AddAgentToProject addAgentToProject;
    @Mock private RemoveAgentFromProject removeAgentFromProject;

    @BeforeEach
    void setUp() {
        this.agentProjectController = new AgentProjectController(this.createAgentProjectApiMapper, this.agentApiMapper,
                this.createAgentProject, this.getAgentProjects, this.getAgentProject,
                this.patchAgentProjectApiMapper, this.patchAgentProject, this.deleteAgentProject, this.getProjectAgents,
                this.addAgentToProject, this.removeAgentFromProject);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.createAgentProjectApiMapper, this.agentApiMapper, this.createAgentProject, this.getAgentProjects, this.getAgentProject,
                this.patchAgentProjectApiMapper, this.patchAgentProject, this.deleteAgentProject, this.getProjectAgents, this.addAgentToProject,
                this.removeAgentFromProject);
    }

    @Test
    void givenCreateAgentProjectRequestDto_whenCreateAgentProject_thenReturnCreatedResponse() {
        //given
        final CreateAgentProjectRequestDTO requestDto = mock(CreateAgentProjectRequestDTO.class);
        final CreateAgentProjectRequest request = mock(CreateAgentProjectRequest.class);
        final AgentProject response = mock(AgentProject.class);
        final AgentProjectDTO responseDto = mock(AgentProjectDTO.class);
        when(this.createAgentProjectApiMapper.asCreateAgentProjectRequest(requestDto)).thenReturn(request);
        when(this.createAgentProject.execute(request)).thenReturn(response);
        when(this.agentApiMapper.asAgentProjectDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentProjectDTO> actual = this.agentProjectController.createAgentProject(requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(responseDto));
        verify(this.createAgentProjectApiMapper).asCreateAgentProjectRequest(requestDto);
        verify(this.createAgentProject).execute(request);
        verify(this.agentApiMapper).asAgentProjectDto(response);
    }

    @Test
    void givenPageAndSize_whenGetAgentProjects_thenReturnProjectsPageResponseDto() {
        //given
        final AgentProjectsPageResponse response = mock(AgentProjectsPageResponse.class);
        final AgentProjectsPageResponseDTO responseDto = mock(AgentProjectsPageResponseDTO.class);
        when(this.getAgentProjects.execute(1, 20)).thenReturn(response);
        when(this.agentApiMapper.asAgentProjectsPageResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentProjectsPageResponseDTO> actual = this.agentProjectController.getAgentProjects(1, 20);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentProjects).execute(1, 20);
        verify(this.agentApiMapper).asAgentProjectsPageResponseDto(response);
    }

    @Test
    void givenProjectId_whenGetAgentProject_thenReturnProjectDto() {
        //given
        final UUID projectId = UUID.randomUUID();
        final AgentProject response = mock(AgentProject.class);
        final AgentProjectDTO responseDto = mock(AgentProjectDTO.class);
        when(this.getAgentProject.execute(projectId)).thenReturn(response);
        when(this.agentApiMapper.asAgentProjectDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentProjectDTO> actual = this.agentProjectController.getAgentProject(projectId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentProject).execute(projectId);
        verify(this.agentApiMapper).asAgentProjectDto(response);
    }

    @Test
    void givenPatchRequest_whenPatchAgentProject_thenReturnProjectDto() {
        //given
        final UUID projectId = UUID.randomUUID();
        final PatchAgentProjectRequestDTO requestDto = mock(PatchAgentProjectRequestDTO.class);
        final PatchAgentProjectRequest request = mock(PatchAgentProjectRequest.class);
        final AgentProject response = mock(AgentProject.class);
        final AgentProjectDTO responseDto = mock(AgentProjectDTO.class);
        when(this.patchAgentProjectApiMapper.asPatchAgentProjectRequest(requestDto)).thenReturn(request);
        when(this.patchAgentProject.execute(projectId, request)).thenReturn(response);
        when(this.agentApiMapper.asAgentProjectDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentProjectDTO> actual = this.agentProjectController.patchAgentProject(projectId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.patchAgentProjectApiMapper).asPatchAgentProjectRequest(requestDto);
        verify(this.patchAgentProject).execute(projectId, request);
        verify(this.agentApiMapper).asAgentProjectDto(response);
    }

    @Test
    void givenProjectId_whenDeleteAgentProject_thenReturnNoContent() {
        //given
        final UUID projectId = UUID.randomUUID();

        //when
        final ResponseEntity<Void> actual = this.agentProjectController.deleteAgentProject(projectId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.noContent().build());
        verify(this.deleteAgentProject).execute(projectId);
    }

    @Test
    void givenProjectId_whenListAgentProjectAgents_thenReturnProjectAgentsResponse() {
        //given
        final UUID projectId = UUID.randomUUID();
        final ProjectAgentsResponse response = mock(ProjectAgentsResponse.class);
        final ProjectAgentsResponseDTO responseDto = mock(ProjectAgentsResponseDTO.class);
        when(this.getProjectAgents.execute(projectId)).thenReturn(response);
        when(this.agentApiMapper.asProjectAgentsResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<ProjectAgentsResponseDTO> actual = this.agentProjectController.listAgentProjectAgents(projectId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getProjectAgents).execute(projectId);
        verify(this.agentApiMapper).asProjectAgentsResponseDto(response);
    }

    @Test
    void givenRequest_whenAddAgentToProject_thenReturnProjectAgentResponse() {
        //given
        final UUID projectId = UUID.randomUUID();
        final AddAgentToProjectRequestDTO requestDto = mock(AddAgentToProjectRequestDTO.class);
        final AddAgentToProjectRequest request = mock(AddAgentToProjectRequest.class);
        final ProjectAgent response = mock(ProjectAgent.class);
        final ProjectAgentResponseDTO responseDto = mock(ProjectAgentResponseDTO.class);
        when(this.agentApiMapper.asAddAgentToProjectRequest(requestDto)).thenReturn(request);
        when(this.addAgentToProject.execute(projectId, request)).thenReturn(response);
        when(this.agentApiMapper.asProjectAgentResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<ProjectAgentResponseDTO> actual = this.agentProjectController.addAgentToProject(projectId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.agentApiMapper).asAddAgentToProjectRequest(requestDto);
        verify(this.addAgentToProject).execute(projectId, request);
        verify(this.agentApiMapper).asProjectAgentResponseDto(response);
    }

    @Test
    void givenProjectIdAndAgentId_whenRemoveAgentFromProject_thenReturnNoContent() {
        //given
        final UUID projectId = UUID.randomUUID();
        final UUID agentId = UUID.randomUUID();

        //when
        final ResponseEntity<Void> actual = this.agentProjectController.removeAgentFromProject(projectId, agentId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.noContent().build());
        verify(this.removeAgentFromProject).execute(projectId, agentId);
    }
}
