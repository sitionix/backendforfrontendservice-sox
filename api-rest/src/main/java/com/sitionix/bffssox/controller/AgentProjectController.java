package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AgentProjectApi;
import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.mapper.AgentApiMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectApiMapper;
import com.sitionix.bffssox.usecase.CreateAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProjects;
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
}
