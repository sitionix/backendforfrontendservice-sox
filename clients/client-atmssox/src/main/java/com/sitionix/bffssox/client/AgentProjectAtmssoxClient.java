package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentProjectApi;
import com.app_afesox.atmssox.client.dto.AgentProjectDTO;
import com.app_afesox.atmssox.client.dto.AgentProjectsPageResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentProjectRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentProjectClientMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentProjectAtmssoxClient implements AgentProjectClient {

    private final AgentProjectApi agentProjectApi;
    private final CreateAgentProjectClientMapper createAgentProjectClientMapper;
    private final PatchAgentProjectClientMapper patchAgentProjectClientMapper;
    private final AgentClientMapper agentClientMapper;
    private final AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    public AgentProject createAgentProject(final CreateAgentProjectRequest request) {
        final CreateAgentProjectRequestDTO requestDTO = this.createAgentProjectClientMapper.asCreateAgentProjectRequestDto(request);
        final AgentProjectDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentProjectApi.createAgentProject(requestDTO));
        return this.agentClientMapper.asAgentProject(responseDTO);
    }

    public AgentProjectsPageResponse getAgentProjects(final Integer page, final Integer size) {
        final AgentProjectsPageResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentProjectApi.getAgentProjects(page, size)
        );
        return this.agentClientMapper.asAgentProjectsPageResponse(responseDTO);
    }

    public AgentProject getAgentProject(final UUID projectId) {
        final AgentProjectDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentProjectApi.getAgentProject(projectId));
        return this.agentClientMapper.asAgentProject(responseDTO);
    }

    public AgentProject patchAgentProject(final UUID projectId, final PatchAgentProjectRequest request) {
        final PatchAgentProjectRequestDTO requestDTO = this.patchAgentProjectClientMapper.asPatchAgentProjectRequestDto(request);
        final AgentProjectDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentProjectApi.patchAgentProject(projectId, requestDTO));
        return this.agentClientMapper.asAgentProject(responseDTO);
    }

    public void deleteAgentProject(final UUID projectId) {
        this.atmssoxClientCallExecutor.execute(() -> {
            this.agentProjectApi.deleteAgentProject(projectId);
            return null;
        });
    }
}
