package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AgentChatApi;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.usecase.GetAgentChatExecution;
import com.sitionix.bffssox.usecase.SubmitAgentChatExecution;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentChatController implements AgentChatApi {

    private final ChatAgentApiMapper chatAgentApiMapper;
    private final SubmitAgentChatExecution submitAgentChatExecution;
    private final GetAgentChatExecution getAgentChatExecution;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SubmitChatExecutionResponseDTO> submitAgentChatExecution(final UUID agentId,
                                                                                    @Valid final ChatAgentRequestDTO chatAgentRequestDTO,
                                                                                    final String idempotencyKey) {
        final ChatAgentRequest request = this.chatAgentApiMapper.asChatAgentRequest(chatAgentRequestDTO);
        final SubmitChatExecutionResponse response = this.submitAgentChatExecution.execute(agentId, request, idempotencyKey);
        return ResponseEntity.accepted().body(this.chatAgentApiMapper.asSubmitChatExecutionResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SubmitChatExecutionResponseDTO> submitAgentChatExecutionByExecutionsPath(final UUID agentId,
                                                                                                    @Valid final ChatAgentRequestDTO chatAgentRequestDTO,
                                                                                                    final String idempotencyKey) {
        return this.submitAgentChatExecution(agentId, chatAgentRequestDTO, idempotencyKey);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChatExecutionDTO> getAgentChatExecution(final UUID agentId,
                                                                  final UUID executionId,
                                                                  final UUID conversationId) {
        final ChatExecution response = this.getAgentChatExecution.execute(agentId, executionId, conversationId);
        return ResponseEntity.ok(this.chatAgentApiMapper.asChatExecutionDto(response));
    }
}
