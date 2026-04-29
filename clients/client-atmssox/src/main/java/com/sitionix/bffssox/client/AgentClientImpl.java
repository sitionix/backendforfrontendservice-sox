package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.invoker.ApiClient;
import com.app_afesox.atmssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.AcceptAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleAuthorTypeDTO;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleStatusDTO;
import com.app_afesox.atmssox.client.dto.AgentRulesResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.AgentRuleClientMapper;
import com.sitionix.bffssox.mapper.ChatAgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentClientMapper;
import java.util.UUID;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentClientImpl implements com.sitionix.bffssox.client.AgentClient {

    private final AgentApi agentApi;

    private final CreateAgentClientMapper createAgentClientMapper;

    private final AgentClientMapper agentClientMapper;

    private final AgentRuleClientMapper agentRuleClientMapper;

    private final PatchAgentClientMapper patchAgentClientMapper;

    private final ChatAgentClientMapper chatAgentClientMapper;

    private final ApiClient atmssoxClient;

    private final AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @Override
    public Agent createAgent(final CreateAgentRequest request) {
        final CreateAgentRequestDTO requestDTO = this.createAgentClientMapper.asCreateAgentRequestDto(request);
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.createAgent(requestDTO)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public AgentsResponse getAgents() {
        final AgentsResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(this.agentApi::getAgents);
        return this.agentClientMapper.asAgentsResponse(responseDTO);
    }

    @Override
    public Agent getAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.getAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public AgentConversationsResponse getAgentConversations(final UUID agentId) {
        final AgentConversationsResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.getAgentConversations(agentId)
        );
        return this.chatAgentClientMapper.asAgentConversationsResponse(responseDTO);
    }

    @Override
    public AgentConversationDetails getAgentConversation(final UUID conversationId) {
        final AgentConversationDetailsDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.getAgentConversation(conversationId)
        );
        return this.chatAgentClientMapper.asAgentConversationDetails(responseDTO);
    }

    @Override
    public Agent activateAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.activateAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public Agent archiveAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.archiveAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public Agent restoreAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.restoreAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public Agent deleteAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.deleteAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public AgentRulesResponse getAgentRules(final UUID agentId, final GetAgentRulesQuery query) {
        final AgentRuleStatusDTO status = query == null || query.status() == null ? null : AgentRuleStatusDTO.fromValue(query.status());
        final AgentRuleAuthorTypeDTO authorType = query == null || query.authorType() == null
                ? null
                : AgentRuleAuthorTypeDTO.fromValue(query.authorType());
        final AgentRulesResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.getAgentRules(agentId, status, authorType)
        );
        return this.agentRuleClientMapper.asAgentRulesResponse(responseDTO);
    }

    @Override
    public AgentRule createAgentRule(final UUID agentId, final CreateAgentRuleRequest request) {
        final CreateAgentRuleRequestDTO requestDTO = this.agentRuleClientMapper.asCreateAgentRuleRequestDto(request);
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.createAgentRule(agentId, requestDTO)
        );
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    @Override
    public AgentRule patchAgentRule(final UUID agentId, final UUID ruleId, final PatchAgentRuleRequest request) {
        final PatchAgentRuleRequestDTO requestDTO = this.agentRuleClientMapper.asPatchAgentRuleRequestDto(request);
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.patchAgentRule(agentId, ruleId, requestDTO)
        );
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    @Override
    public AgentRule acceptAgentRule(final UUID agentId, final UUID ruleId, final AcceptAgentRuleRequest request) {
        final AcceptAgentRuleRequestDTO requestDTO = request == null ? null : this.agentRuleClientMapper.asAcceptAgentRuleRequestDto(request);
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.acceptAgentRule(agentId, ruleId, requestDTO)
        );
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    @Override
    public AgentRule rejectAgentRule(final UUID agentId, final UUID ruleId) {
        final AgentRuleDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.rejectAgentRule(agentId, ruleId)
        );
        return this.agentRuleClientMapper.asAgentRule(responseDTO);
    }

    @Override
    public DeleteAgentRuleResponse deleteAgentRule(final UUID agentId, final UUID ruleId) {
        final DeleteAgentRuleResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.deleteAgentRule(agentId, ruleId)
        );
        return this.agentRuleClientMapper.asDeleteAgentRuleResponse(responseDTO);
    }

    @Override
    public ChatAgentResponse chatAgent(final UUID agentId, final ChatAgentRequest request) {
        throw new UnsupportedOperationException("Synchronous chatAgent flow is removed; use submitAgentChatExecution");
    }

    @Override
    public SubmitChatExecutionResponse submitAgentChatExecution(final UUID agentId,
                                                                final ChatAgentRequest request,
                                                                final String idempotencyKey) {
        final ChatAgentRequestDTO requestDTO = this.chatAgentClientMapper.asChatAgentRequestDto(request);
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        if (idempotencyKey != null) {
            headerParams.add("Idempotency-Key", idempotencyKey);
        }
        final SubmitChatExecutionResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.atmssoxClient.invokeAPI(
                        "/api/v1/agents/{agentId}/chat/executions",
                        HttpMethod.POST,
                        java.util.Map.of("agentId", agentId),
                        queryParams,
                        requestDTO,
                        headerParams,
                        new LinkedMultiValueMap<>(),
                        new LinkedMultiValueMap<>(),
                        java.util.List.of(MediaType.APPLICATION_JSON),
                        MediaType.APPLICATION_JSON,
                        new String[0],
                        new ParameterizedTypeReference<SubmitChatExecutionResponseDTO>() { }
                ).getBody()
        );
        return this.chatAgentClientMapper.asSubmitChatExecutionResponse(responseDTO);
    }

    @Override
    public ChatExecution getAgentChatExecution(final UUID agentId, final UUID executionId, final UUID conversationId) {
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (conversationId != null) {
            queryParams.add("conversationId", conversationId.toString());
        }
        final ChatExecutionDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.atmssoxClient.invokeAPI(
                        "/api/v1/agents/{agentId}/chat/executions/{executionId}",
                        HttpMethod.GET,
                        java.util.Map.of("agentId", agentId, "executionId", executionId),
                        queryParams,
                        null,
                        new HttpHeaders(),
                        new LinkedMultiValueMap<>(),
                        new LinkedMultiValueMap<>(),
                        java.util.List.of(MediaType.APPLICATION_JSON),
                        null,
                        new String[0],
                        new ParameterizedTypeReference<ChatExecutionDTO>() { }
                ).getBody()
        );
        return this.chatAgentClientMapper.asChatExecution(responseDTO);
    }

    @Override
    public Agent patchAgent(final UUID agentId, final PatchAgentRequest request) {
        final PatchAgentRequestDTO requestDTO = this.patchAgentClientMapper.asPatchAgentRequestDto(request);
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.patchAgent(agentId, requestDTO)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }
}
