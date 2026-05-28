package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AgentProjectApi;
import com.app_afesox.bffssox.api_first.dto.AddAgentToProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.bffssox.api_first.dto.GetAgentProjectFlowPaletteResponseDTO;
import com.app_afesox.bffssox.api_first.dto.GetAgentProjectFlowResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentProjectRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectAgentsResponseDTO;
import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import com.sitionix.bffssox.domain.AgentProjectFlowResponse;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import com.sitionix.bffssox.mapper.AgentApiMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectApiMapper;
import com.sitionix.bffssox.mapper.PatchAgentProjectApiMapper;
import com.sitionix.bffssox.usecase.AddAgentToProject;
import com.sitionix.bffssox.usecase.CreateAgentProject;
import com.sitionix.bffssox.usecase.DeleteAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProjectFlow;
import com.sitionix.bffssox.usecase.GetAgentProjectFlowPalette;
import com.sitionix.bffssox.usecase.GetAgentProjects;
import com.sitionix.bffssox.usecase.GetProjectAgents;
import com.sitionix.bffssox.usecase.PatchAgentProject;
import com.sitionix.bffssox.usecase.RemoveAgentFromProject;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentProjectController implements AgentProjectApi {

    private final CreateAgentProjectApiMapper createAgentProjectApiMapper;
    private final AgentApiMapper agentApiMapper;
    private final CreateAgentProject createAgentProject;
    private final GetAgentProjects getAgentProjects;
    private final GetAgentProject getAgentProject;
    private final GetAgentProjectFlow getAgentProjectFlow;
    private final GetAgentProjectFlowPalette getAgentProjectFlowPalette;
    private final PatchAgentProjectApiMapper patchAgentProjectApiMapper;
    private final PatchAgentProject patchAgentProject;
    private final DeleteAgentProject deleteAgentProject;
    private final GetProjectAgents getProjectAgents;
    private final AddAgentToProject addAgentToProject;
    private final RemoveAgentFromProject removeAgentFromProject;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentProjectDTO> createAgentProject(@Valid final CreateAgentProjectRequestDTO createAgentProjectRequestDTO) {
        final CreateAgentProjectRequest request = this.createAgentProjectApiMapper.asCreateAgentProjectRequest(createAgentProjectRequestDTO);
        final AgentProject response = this.createAgentProject.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.agentApiMapper.asAgentProjectDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentProjectsPageResponseDTO> getAgentProjects(final Integer page, final Integer size) {
        final AgentProjectsPageResponse response = this.getAgentProjects.execute(page, size);
        return ResponseEntity.ok(this.agentApiMapper.asAgentProjectsPageResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentProjectDTO> getAgentProject(final UUID projectId) {
        final AgentProject response = this.getAgentProject.execute(projectId);
        return ResponseEntity.ok(this.agentApiMapper.asAgentProjectDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetAgentProjectFlowResponseDTO> getAgentProjectFlow(final UUID projectId) {
        final AgentProjectFlowResponse response = this.getAgentProjectFlow.execute(projectId);
        return ResponseEntity.ok(this.agentApiMapper.asGetAgentProjectFlowResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetAgentProjectFlowPaletteResponseDTO> getAgentProjectFlowPalette(final UUID projectId) {
        final AgentProjectFlowPaletteResponse response = this.getAgentProjectFlowPalette.execute(projectId);
        return ResponseEntity.ok(this.agentApiMapper.asGetAgentProjectFlowPaletteResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentProjectDTO> patchAgentProject(final UUID projectId, @Valid final PatchAgentProjectRequestDTO patchAgentProjectRequestDTO) {
        final AgentProject response = this.patchAgentProject.execute(projectId, this.patchAgentProjectApiMapper.asPatchAgentProjectRequest(patchAgentProjectRequestDTO));
        return ResponseEntity.ok(this.agentApiMapper.asAgentProjectDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteAgentProject(final UUID projectId) {
        this.deleteAgentProject.execute(projectId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjectAgentsResponseDTO> listAgentProjectAgents(final UUID projectId) {
        final ProjectAgentsResponse response = this.getProjectAgents.execute(projectId);
        return ResponseEntity.ok(this.agentApiMapper.asProjectAgentsResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjectAgentResponseDTO> addAgentToProject(final UUID projectId,
                                                                     @Valid final AddAgentToProjectRequestDTO addAgentToProjectRequestDTO) {
        final AddAgentToProjectRequest request = this.agentApiMapper.asAddAgentToProjectRequest(addAgentToProjectRequestDTO);
        final ProjectAgent response = this.addAgentToProject.execute(projectId, request);
        return ResponseEntity.ok(this.agentApiMapper.asProjectAgentResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> removeAgentFromProject(final UUID projectId, final UUID agentId) {
        this.removeAgentFromProject.execute(projectId, agentId);
        return ResponseEntity.noContent().build();
    }
}
