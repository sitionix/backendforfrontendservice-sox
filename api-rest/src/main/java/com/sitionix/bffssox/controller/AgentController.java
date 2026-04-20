package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AgentApi;
import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.AgentsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.mapper.AgentApiMapper;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.mapper.CreateAgentApiMapper;
import com.sitionix.bffssox.mapper.PatchAgentApiMapper;
import com.sitionix.bffssox.usecase.ActivateAgent;
import com.sitionix.bffssox.usecase.ArchiveAgent;
import com.sitionix.bffssox.usecase.ChatAgent;
import com.sitionix.bffssox.usecase.CreateAgent;
import com.sitionix.bffssox.usecase.DeleteAgent;
import com.sitionix.bffssox.usecase.GetAgent;
import com.sitionix.bffssox.usecase.GetAgents;
import com.sitionix.bffssox.usecase.PatchAgent;
import com.sitionix.bffssox.usecase.RestoreAgent;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentController implements AgentApi {

    private final CreateAgentApiMapper createAgentApiMapper;

    private final AgentApiMapper agentApiMapper;

    private final CreateAgent createAgent;

    private final PatchAgentApiMapper patchAgentApiMapper;

    private final PatchAgent patchAgent;

    private final ChatAgentApiMapper chatAgentApiMapper;

    private final ChatAgent chatAgent;

    private final GetAgents getAgents;

    private final GetAgent getAgent;

    private final ActivateAgent activateAgent;

    private final ArchiveAgent archiveAgent;

    private final RestoreAgent restoreAgent;

    private final DeleteAgent deleteAgent;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentDTO> createAgent(@Valid final CreateAgentRequestDTO createAgentRequestDTO) {
        final CreateAgentRequest request = this.createAgentApiMapper.asCreateAgentRequest(createAgentRequestDTO);
        final Agent response = this.createAgent.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.agentApiMapper.asAgentDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentsResponseDTO> getAgents() {
        final AgentsResponse response = this.getAgents.execute();
        return ResponseEntity.ok(this.agentApiMapper.asAgentsResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentDTO> getAgent(final UUID agentId) {
        final Agent response = this.getAgent.execute(agentId);
        return ResponseEntity.ok(this.agentApiMapper.asAgentDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentDTO> activateAgent(final UUID agentId) {
        final Agent response = this.activateAgent.execute(agentId);
        return ResponseEntity.ok(this.agentApiMapper.asAgentDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentDTO> archiveAgent(final UUID agentId) {
        final Agent response = this.archiveAgent.execute(agentId);
        return ResponseEntity.ok(this.agentApiMapper.asAgentDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentDTO> restoreAgent(final UUID agentId) {
        final Agent response = this.restoreAgent.execute(agentId);
        return ResponseEntity.ok(this.agentApiMapper.asAgentDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentDTO> deleteAgent(final UUID agentId) {
        final Agent response = this.deleteAgent.execute(agentId);
        return ResponseEntity.ok(this.agentApiMapper.asAgentDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChatAgentResponseDTO> chatAgent(final UUID agentId, @Valid final ChatAgentRequestDTO chatAgentRequestDTO) {
        final ChatAgentResponse response = this.chatAgent.execute(agentId, this.chatAgentApiMapper.asChatAgentRequest(chatAgentRequestDTO));
        return ResponseEntity.ok(this.chatAgentApiMapper.asChatAgentResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentDTO> patchAgent(final UUID agentId, @Valid final PatchAgentRequestDTO patchAgentRequestDTO) {
        if (!this.hasAnyPatchField(patchAgentRequestDTO)) {
            throw new IllegalArgumentException("Patch payload cannot be empty");
        }
        final Agent response = this.patchAgent.execute(agentId, this.patchAgentApiMapper.asPatchAgentRequest(patchAgentRequestDTO));
        return ResponseEntity.ok(this.agentApiMapper.asAgentDto(response));
    }

    private boolean hasAnyPatchField(final PatchAgentRequestDTO patchAgentRequestDTO) {
        return patchAgentRequestDTO.getName() != null
                || patchAgentRequestDTO.getDescription() != null
                || patchAgentRequestDTO.getInstruction() != null;
    }
}
