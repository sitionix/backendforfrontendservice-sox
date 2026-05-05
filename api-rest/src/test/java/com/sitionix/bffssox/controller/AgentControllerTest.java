package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AgentDTO;
import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import com.sitionix.bffssox.mapper.AgentApiMapper;
import com.sitionix.bffssox.mapper.AgentRuleApiMapper;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.mapper.CreateAgentApiMapper;
import com.sitionix.bffssox.mapper.PatchAgentApiMapper;
import com.sitionix.bffssox.usecase.ActivateAgent;
import com.sitionix.bffssox.usecase.ArchiveAgent;
import com.sitionix.bffssox.usecase.CreateAgent;
import com.sitionix.bffssox.usecase.CreateAgentRule;
import com.sitionix.bffssox.usecase.DeleteAgent;
import com.sitionix.bffssox.usecase.DeleteAgentConversation;
import com.sitionix.bffssox.usecase.DeleteAgentRule;
import com.sitionix.bffssox.usecase.GetAgent;
import com.sitionix.bffssox.usecase.GetAgentChatExecution;
import com.sitionix.bffssox.usecase.GetAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversations;
import com.sitionix.bffssox.usecase.GetAgentRules;
import com.sitionix.bffssox.usecase.GetAgents;
import com.sitionix.bffssox.usecase.PatchAgent;
import com.sitionix.bffssox.usecase.PatchAgentRule;
import com.sitionix.bffssox.usecase.RestoreAgent;
import com.sitionix.bffssox.usecase.SubmitAgentChatExecution;
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
class AgentControllerTest {

    private AgentController agentController;

    @Mock private CreateAgentApiMapper createAgentApiMapper;
    @Mock private AgentApiMapper agentApiMapper;
    @Mock private AgentRuleApiMapper agentRuleApiMapper;
    @Mock private CreateAgent createAgent;
    @Mock private PatchAgentApiMapper patchAgentApiMapper;
    @Mock private PatchAgent patchAgent;
    @Mock private ChatAgentApiMapper chatAgentApiMapper;
    @Mock private SubmitAgentChatExecution submitAgentChatExecution;
    @Mock private GetAgentChatExecution getAgentChatExecution;
    @Mock private GetAgents getAgents;
    @Mock private GetAgent getAgent;
    @Mock private GetAgentConversations getAgentConversations;
    @Mock private GetAgentConversation getAgentConversation;
    @Mock private ActivateAgent activateAgent;
    @Mock private ArchiveAgent archiveAgent;
    @Mock private RestoreAgent restoreAgent;
    @Mock private DeleteAgent deleteAgent;
    @Mock private GetAgentRules getAgentRules;
    @Mock private CreateAgentRule createAgentRule;
    @Mock private PatchAgentRule patchAgentRule;
    @Mock private DeleteAgentRule deleteAgentRule;
    @Mock private DeleteAgentConversation deleteAgentConversation;

    @BeforeEach
    void setUp() {
        this.agentController = new AgentController(
                this.createAgentApiMapper,
                this.agentApiMapper,
                this.agentRuleApiMapper,
                this.createAgent,
                this.patchAgentApiMapper,
                this.patchAgent,
                this.chatAgentApiMapper,
                this.submitAgentChatExecution,
                this.getAgentChatExecution,
                this.getAgents,
                this.getAgent,
                this.getAgentConversations,
                this.getAgentConversation,
                this.activateAgent,
                this.archiveAgent,
                this.restoreAgent,
                this.deleteAgent,
                this.getAgentRules,
                this.createAgentRule,
                this.patchAgentRule,
                this.deleteAgentRule,
                this.deleteAgentConversation
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.createAgentApiMapper,
                this.agentApiMapper,
                this.agentRuleApiMapper,
                this.createAgent,
                this.patchAgentApiMapper,
                this.patchAgent,
                this.chatAgentApiMapper,
                this.submitAgentChatExecution,
                this.getAgentChatExecution,
                this.getAgents,
                this.getAgent,
                this.getAgentConversations,
                this.getAgentConversation,
                this.activateAgent,
                this.archiveAgent,
                this.restoreAgent,
                this.deleteAgent,
                this.getAgentRules,
                this.createAgentRule,
                this.patchAgentRule,
                this.deleteAgentRule,
                this.deleteAgentConversation
        );
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

    @Test
    void givenSubmitExecutionRequest_whenSubmitAgentChatExecution_thenReturnAcceptedEnvelope() {
        //given
        final UUID agentId = UUID.fromString("76f023a2-cb0c-44d5-970d-053f4af51f6b");
        final ChatAgentRequestDTO requestDto = mock(ChatAgentRequestDTO.class);
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final SubmitChatExecutionResponse response = mock(SubmitChatExecutionResponse.class);
        final SubmitChatExecutionResponseDTO responseDto = mock(SubmitChatExecutionResponseDTO.class);

        when(this.chatAgentApiMapper.asChatAgentRequest(requestDto)).thenReturn(request);
        when(this.submitAgentChatExecution.execute(agentId, request, "idem")).thenReturn(response);
        when(this.chatAgentApiMapper.asSubmitChatExecutionResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<SubmitChatExecutionResponseDTO> actual =
                this.agentController.submitAgentChatExecution(agentId, requestDto, "idem");

        //then
        assertThat(actual).isEqualTo(ResponseEntity.accepted().body(responseDto));
        verify(this.chatAgentApiMapper).asChatAgentRequest(requestDto);
        verify(this.submitAgentChatExecution).execute(agentId, request, "idem");
        verify(this.chatAgentApiMapper).asSubmitChatExecutionResponseDto(response);
    }

    @Test
    void givenSubmitExecutionRequest_whenSubmitAgentChatExecutionByExecutionsPath_thenDelegateToSubmitFlow() {
        //given
        final UUID agentId = UUID.fromString("f318a8d4-c8d5-44f2-ad4b-b3a6a900e955");
        final ChatAgentRequestDTO requestDto = mock(ChatAgentRequestDTO.class);
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final SubmitChatExecutionResponse response = mock(SubmitChatExecutionResponse.class);
        final SubmitChatExecutionResponseDTO responseDto = mock(SubmitChatExecutionResponseDTO.class);

        when(this.chatAgentApiMapper.asChatAgentRequest(requestDto)).thenReturn(request);
        when(this.submitAgentChatExecution.execute(agentId, request, "idem")).thenReturn(response);
        when(this.chatAgentApiMapper.asSubmitChatExecutionResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<SubmitChatExecutionResponseDTO> actual =
                this.agentController.submitAgentChatExecutionByExecutionsPath(agentId, requestDto, "idem");

        //then
        assertThat(actual).isEqualTo(ResponseEntity.accepted().body(responseDto));
        verify(this.chatAgentApiMapper).asChatAgentRequest(requestDto);
        verify(this.submitAgentChatExecution).execute(agentId, request, "idem");
        verify(this.chatAgentApiMapper).asSubmitChatExecutionResponseDto(response);
    }

    @Test
    void givenExecutionLookupRequest_whenGetAgentChatExecution_thenReturnOkResponse() {
        //given
        final UUID agentId = UUID.fromString("1f723177-ec03-4506-9011-cf0b39c97c61");
        final UUID executionId = UUID.fromString("d67d95cb-8fcb-4a09-8f17-4ad5295a784b");
        final UUID conversationId = UUID.fromString("3b08ad4e-13f6-4d83-ab5d-dfe04ccebe4f");
        final ChatExecution response = mock(ChatExecution.class);
        final ChatExecutionDTO responseDto = mock(ChatExecutionDTO.class);

        when(this.getAgentChatExecution.execute(agentId, executionId, conversationId)).thenReturn(response);
        when(this.chatAgentApiMapper.asChatExecutionDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<ChatExecutionDTO> actual =
                this.agentController.getAgentChatExecution(agentId, executionId, conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentChatExecution).execute(agentId, executionId, conversationId);
        verify(this.chatAgentApiMapper).asChatExecutionDto(response);
    }

    @Test
    void givenConversationId_whenDeleteAgentConversation_thenReturnNoContent() {
        //given
        final UUID conversationId = UUID.fromString("4dd6a86d-7de1-4187-9624-fc912633b102");

        //when
        final ResponseEntity<Void> actual = this.agentController.deleteAgentConversation(conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.noContent().build());
        verify(this.deleteAgentConversation).execute(conversationId);
    }
}
