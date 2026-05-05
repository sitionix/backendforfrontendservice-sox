package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.client.dto.AcceptAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleAuthorTypeDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleDTO;
import com.app_afesox.atmssox.client.dto.AgentRuleStatusDTO;
import com.app_afesox.atmssox.client.dto.AgentRulesResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.DeleteAgentRuleResponseDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRuleRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentRequest;
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
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentClientImplTest {

    private AgentClient agentClient;

    @Mock
    private AgentApi agentApi;

    @Mock
    private CreateAgentClientMapper createAgentClientMapper;

    @Mock
    private AgentClientMapper agentClientMapper;

    @Mock
    private AgentRuleClientMapper agentRuleClientMapper;

    @Mock
    private PatchAgentClientMapper patchAgentClientMapper;

    @Mock
    private ChatAgentClientMapper chatAgentClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentClient = new AgentClientImpl(
                this.agentApi,
                this.createAgentClientMapper,
                this.agentClientMapper,
                this.agentRuleClientMapper,
                this.patchAgentClientMapper,
                this.chatAgentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.agentApi,
                this.createAgentClientMapper,
                this.agentClientMapper,
                this.agentRuleClientMapper,
                this.patchAgentClientMapper,
                this.chatAgentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @Test
    void givenCreateAgentRequest_whenCreateAgent_thenReturnAgent() {
        //given
        final CreateAgentRequest request = mock(CreateAgentRequest.class);
        final CreateAgentRequestDTO requestDTO = mock(CreateAgentRequestDTO.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent response = mock(Agent.class);

        when(this.createAgentClientMapper.asCreateAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.createAgent(requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(response);

        //when
        final Agent actual = this.agentClient.createAgent(request);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.createAgentClientMapper).asCreateAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).createAgent(requestDTO);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenGetAgentsRequest_whenGetAgents_thenReturnAgentsResponse() {
        //given
        final AgentsResponseDTO responseDTO = mock(AgentsResponseDTO.class);
        final AgentsResponse response = mock(AgentsResponse.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentsResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgents()).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentsResponse(responseDTO)).thenReturn(response);

        //when
        final AgentsResponse actual = this.agentClient.getAgents();

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgents();
        verify(this.agentClientMapper).asAgentsResponse(responseDTO);
    }

    @Test
    void givenAgentId_whenGetAgent_thenReturnAgent() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent response = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgent(agentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(response);

        //when
        final Agent actual = this.agentClient.getAgent(agentId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgent(agentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenGetAgentConversations_thenReturnAgentConversationsResponse() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final AgentConversationsResponseDTO responseDTO = mock(AgentConversationsResponseDTO.class);
        final AgentConversationsResponse response = mock(AgentConversationsResponse.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentConversationsResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgentConversations(agentId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asAgentConversationsResponse(responseDTO)).thenReturn(response);

        //when
        final AgentConversationsResponse actual = this.agentClient.getAgentConversations(agentId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgentConversations(agentId);
        verify(this.chatAgentClientMapper).asAgentConversationsResponse(responseDTO);
    }

    @Test
    void givenConversationId_whenGetAgentConversation_thenReturnAgentConversationDetails() {
        //given
        final UUID conversationId = UUID.fromString("cab3fa9e-c59f-47cb-8627-1c19698ef5f3");
        final AgentConversationDetailsDTO responseDTO = mock(AgentConversationDetailsDTO.class);
        final AgentConversationDetails response = mock(AgentConversationDetails.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentConversationDetailsDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgentConversation(conversationId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asAgentConversationDetails(responseDTO)).thenReturn(response);

        //when
        final AgentConversationDetails actual = this.agentClient.getAgentConversation(conversationId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgentConversation(conversationId);
        verify(this.chatAgentClientMapper).asAgentConversationDetails(responseDTO);
    }

    @Test
    void givenConversationId_whenDeleteAgentConversation_thenCallDownstreamDelete() {
        //given
        final UUID conversationId = UUID.fromString("34ce1743-04f0-4066-8fbe-4f965e4b4904");
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<Void> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        //when
        this.agentClient.deleteAgentConversation(conversationId);

        //then
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).deleteAgentConversation(conversationId);
    }

    @Test
    void givenPatchAgentRequest_whenPatchAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final PatchAgentRequest request = mock(PatchAgentRequest.class);
        final PatchAgentRequestDTO requestDTO = mock(PatchAgentRequestDTO.class);
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.patchAgentClientMapper.asPatchAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.patchAgent(givenAgentId, requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.patchAgent(givenAgentId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.patchAgentClientMapper).asPatchAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).patchAgent(givenAgentId, requestDTO);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenActivateAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("88e2673a-273a-4cf4-a94f-96f4df311ea9");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.activateAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.activateAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).activateAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenArchiveAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("f8f58985-8fa8-4412-8f1e-7955f0f8f85e");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.archiveAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.archiveAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).archiveAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenRestoreAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("c1db6b3a-2ee2-4f59-889b-ec3e8e343973");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.restoreAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.restoreAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).restoreAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenAgentId_whenDeleteAgent_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("78ad5fab-af5a-4667-a150-33970a9f72b0");
        final AgentDTO responseDTO = mock(AgentDTO.class);
        final Agent expected = mock(Agent.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.deleteAgent(givenAgentId)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgent(responseDTO)).thenReturn(expected);

        //when
        final Agent actual = this.agentClient.deleteAgent(givenAgentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).deleteAgent(givenAgentId);
        verify(this.agentClientMapper).asAgent(responseDTO);
    }

    @Test
    void givenChatAgentRequest_whenSubmitAgentChatExecution_thenReturnExecutionAck() {
        //given
        final UUID givenAgentId = UUID.fromString("7ac2f8c1-3d66-4cb4-95d9-27df5c66bf20");
        final UUID clientRequestId = UUID.fromString("8ecef151-3f6a-46a8-a9a4-1f61f65b6f09");
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final ChatAgentRequestDTO requestDTO = ChatAgentRequestDTO.builder()
                .clientRequestId(clientRequestId)
                .message("message")
                .build();
        final SubmitChatExecutionResponseDTO responseDTO = mock(SubmitChatExecutionResponseDTO.class);
        final SubmitChatExecutionResponse expected = mock(SubmitChatExecutionResponse.class);

        when(this.chatAgentClientMapper.asChatAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<SubmitChatExecutionResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.submitAgentChatExecutionByExecutionsPath(givenAgentId, requestDTO, "idem-key")).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asSubmitChatExecutionResponse(responseDTO)).thenReturn(expected);

        //when
        final SubmitChatExecutionResponse actual = this.agentClient.submitAgentChatExecution(givenAgentId, request, "idem-key");

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.chatAgentClientMapper).asChatAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).submitAgentChatExecutionByExecutionsPath(
                eq(givenAgentId),
                argThat(dto -> dto != null && clientRequestId.equals(dto.getClientRequestId())),
                eq("idem-key")
        );
        verify(this.chatAgentClientMapper).asSubmitChatExecutionResponse(responseDTO);
    }

    @Test
    void givenChatAgentRequestAndNullIdempotencyKey_whenSubmitAgentChatExecution_thenDoNotSendIdempotencyHeader() {
        //given
        final UUID givenAgentId = UUID.fromString("7ac2f8c1-3d66-4cb4-95d9-27df5c66bf20");
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final ChatAgentRequestDTO requestDTO = mock(ChatAgentRequestDTO.class);
        final SubmitChatExecutionResponseDTO responseDTO = mock(SubmitChatExecutionResponseDTO.class);
        final SubmitChatExecutionResponse expected = mock(SubmitChatExecutionResponse.class);

        when(this.chatAgentClientMapper.asChatAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<SubmitChatExecutionResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.submitAgentChatExecutionByExecutionsPath(givenAgentId, requestDTO, null)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asSubmitChatExecutionResponse(responseDTO)).thenReturn(expected);

        //when
        final SubmitChatExecutionResponse actual = this.agentClient.submitAgentChatExecution(givenAgentId, request, null);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.chatAgentClientMapper).asChatAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).submitAgentChatExecutionByExecutionsPath(givenAgentId, requestDTO, null);
        verify(this.chatAgentClientMapper).asSubmitChatExecutionResponse(responseDTO);
    }

    @Test
    void givenExecutionIdentifiers_whenGetAgentChatExecution_thenReturnExecution() {
        //given
        final UUID givenAgentId = UUID.fromString("7ac2f8c1-3d66-4cb4-95d9-27df5c66bf20");
        final UUID givenExecutionId = UUID.fromString("f2a1e248-e001-486f-90d5-b69281eb2ec2");
        final UUID givenConversationId = UUID.fromString("b6f2e9b0-1572-4e88-a430-bca8c7e6434f");
        final ChatExecutionDTO responseDTO = mock(ChatExecutionDTO.class);
        final ChatExecution expected = mock(ChatExecution.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<ChatExecutionDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgentChatExecution(givenAgentId, givenExecutionId, givenConversationId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asChatExecution(responseDTO)).thenReturn(expected);

        //when
        final ChatExecution actual = this.agentClient.getAgentChatExecution(givenAgentId, givenExecutionId, givenConversationId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgentChatExecution(givenAgentId, givenExecutionId, givenConversationId);
        verify(this.chatAgentClientMapper).asChatExecution(responseDTO);
    }

    @Test
    void givenExecutionIdentifiersAndNullConversationId_whenGetAgentChatExecution_thenDoNotSendConversationQueryParam() {
        //given
        final UUID givenAgentId = UUID.fromString("7ac2f8c1-3d66-4cb4-95d9-27df5c66bf20");
        final UUID givenExecutionId = UUID.fromString("f2a1e248-e001-486f-90d5-b69281eb2ec2");
        final ChatExecutionDTO responseDTO = mock(ChatExecutionDTO.class);
        final ChatExecution expected = mock(ChatExecution.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<ChatExecutionDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgentChatExecution(givenAgentId, givenExecutionId, null)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asChatExecution(responseDTO)).thenReturn(expected);

        //when
        final ChatExecution actual = this.agentClient.getAgentChatExecution(givenAgentId, givenExecutionId, null);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgentChatExecution(givenAgentId, givenExecutionId, null);
        verify(this.chatAgentClientMapper).asChatExecution(responseDTO);
    }

    @Test
    void givenAgentId_whenGetAgentRules_thenReturnAgentRulesResponse() {
        //given
        final UUID givenAgentId = UUID.fromString("d9ac51e6-9711-40b8-b5bc-fbafdb9f8c08");
        final GetAgentRulesQuery query = new GetAgentRulesQuery("ACTIVE", "AI");
        final AgentRulesResponseDTO responseDTO = mock(AgentRulesResponseDTO.class);
        final AgentRulesResponse expected = mock(AgentRulesResponse.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentRulesResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgentRules(givenAgentId, AgentRuleStatusDTO.ACTIVE, AgentRuleAuthorTypeDTO.AI)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRulesResponse(responseDTO)).thenReturn(expected);

        //when
        final AgentRulesResponse actual = this.agentClient.getAgentRules(givenAgentId, query);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgentRules(givenAgentId, AgentRuleStatusDTO.ACTIVE, AgentRuleAuthorTypeDTO.AI);
        verify(this.agentRuleClientMapper).asAgentRulesResponse(responseDTO);
    }

    @Test
    void givenAgentIdAndNullQuery_whenGetAgentRules_thenReturnAgentRulesResponse() {
        //given
        final UUID givenAgentId = UUID.fromString("52418b95-6688-4c7e-a7ef-08eeceddd153");
        final AgentRulesResponseDTO responseDTO = mock(AgentRulesResponseDTO.class);
        final AgentRulesResponse expected = mock(AgentRulesResponse.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentRulesResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.getAgentRules(givenAgentId, null, null)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRulesResponse(responseDTO)).thenReturn(expected);

        //when
        final AgentRulesResponse actual = this.agentClient.getAgentRules(givenAgentId, null);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).getAgentRules(givenAgentId, null, null);
        verify(this.agentRuleClientMapper).asAgentRulesResponse(responseDTO);
    }

    @Test
    void givenAgentIdAndRuleRequest_whenCreateAgentRule_thenReturnAgentRule() {
        //given
        final UUID givenAgentId = UUID.fromString("fc22a352-fcc8-4765-a9f8-959518f0689f");
        final CreateAgentRuleRequest request = mock(CreateAgentRuleRequest.class);
        final CreateAgentRuleRequestDTO requestDTO = mock(CreateAgentRuleRequestDTO.class);
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.agentRuleClientMapper.asCreateAgentRuleRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentRuleDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.createAgentRule(givenAgentId, requestDTO)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentClient.createAgentRule(givenAgentId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentRuleClientMapper).asCreateAgentRuleRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).createAgentRule(givenAgentId, requestDTO);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenAgentAndRuleIdsAndPatchRequest_whenPatchAgentRule_thenReturnAgentRule() {
        //given
        final UUID givenAgentId = UUID.fromString("e71cf5eb-35fa-46e2-b75d-8094c2af4e4a");
        final UUID givenRuleId = UUID.fromString("6700e0eb-f6de-4f4d-9a3f-f40eabfe7f2c");
        final PatchAgentRuleRequest request = mock(PatchAgentRuleRequest.class);
        final PatchAgentRuleRequestDTO requestDTO = mock(PatchAgentRuleRequestDTO.class);
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.agentRuleClientMapper.asPatchAgentRuleRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentRuleDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.patchAgentRule(givenAgentId, givenRuleId, requestDTO)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentClient.patchAgentRule(givenAgentId, givenRuleId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentRuleClientMapper).asPatchAgentRuleRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).patchAgentRule(givenAgentId, givenRuleId, requestDTO);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenAgentAndRuleIdsAndNullAcceptRequest_whenAcceptAgentRule_thenReturnAgentRule() {
        //given
        final UUID givenAgentId = UUID.fromString("92456ccf-6595-4f4a-88f7-5f9ed79046fe");
        final UUID givenRuleId = UUID.fromString("e2b9d7cb-f69a-405d-b44f-e95dbd7e08ee");
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentRuleDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.acceptAgentRule(givenAgentId, givenRuleId, null)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentClient.acceptAgentRule(givenAgentId, givenRuleId, null);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).acceptAgentRule(givenAgentId, givenRuleId, null);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenAgentAndRuleIdsAndAcceptRequest_whenAcceptAgentRule_thenReturnAgentRule() {
        //given
        final UUID givenAgentId = UUID.fromString("f46cd205-0809-4fd3-832f-3358f71339df");
        final UUID givenRuleId = UUID.fromString("c22311b0-5b92-4d98-b04e-eb2fbe34f8ca");
        final AcceptAgentRuleRequest request = mock(AcceptAgentRuleRequest.class);
        final AcceptAgentRuleRequestDTO requestDTO = mock(AcceptAgentRuleRequestDTO.class);
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.agentRuleClientMapper.asAcceptAgentRuleRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentRuleDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.acceptAgentRule(givenAgentId, givenRuleId, requestDTO)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentClient.acceptAgentRule(givenAgentId, givenRuleId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentRuleClientMapper).asAcceptAgentRuleRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).acceptAgentRule(givenAgentId, givenRuleId, requestDTO);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenAgentAndRuleIds_whenRejectAgentRule_thenReturnAgentRule() {
        //given
        final UUID givenAgentId = UUID.fromString("465cd2f0-2dd5-4847-bd99-f6bfd8c7f331");
        final UUID givenRuleId = UUID.fromString("60af08a3-0a29-4f27-8a74-f0f29695f90c");
        final AgentRuleDTO responseDTO = mock(AgentRuleDTO.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<AgentRuleDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.rejectAgentRule(givenAgentId, givenRuleId)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asAgentRule(responseDTO)).thenReturn(expected);

        //when
        final AgentRule actual = this.agentClient.rejectAgentRule(givenAgentId, givenRuleId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).rejectAgentRule(givenAgentId, givenRuleId);
        verify(this.agentRuleClientMapper).asAgentRule(responseDTO);
    }

    @Test
    void givenAgentAndRuleIds_whenDeleteAgentRule_thenReturnDeleteAgentRuleResponse() {
        //given
        final UUID givenAgentId = UUID.fromString("46e63917-16f9-4f89-a589-af57209dae6d");
        final UUID givenRuleId = UUID.fromString("e644fd69-b71a-4600-8e3f-2ec99b0afab0");
        final DeleteAgentRuleResponseDTO responseDTO = mock(DeleteAgentRuleResponseDTO.class);
        final DeleteAgentRuleResponse expected = mock(DeleteAgentRuleResponse.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<DeleteAgentRuleResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.agentApi.deleteAgentRule(givenAgentId, givenRuleId)).thenReturn(responseDTO);
        when(this.agentRuleClientMapper.asDeleteAgentRuleResponse(responseDTO)).thenReturn(expected);

        //when
        final DeleteAgentRuleResponse actual = this.agentClient.deleteAgentRule(givenAgentId, givenRuleId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentApi).deleteAgentRule(givenAgentId, givenRuleId);
        verify(this.agentRuleClientMapper).asDeleteAgentRuleResponse(responseDTO);
    }
}
