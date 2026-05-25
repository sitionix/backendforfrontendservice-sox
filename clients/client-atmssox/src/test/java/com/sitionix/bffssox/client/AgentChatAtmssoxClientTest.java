package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentChatApi;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.client.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import com.sitionix.bffssox.mapper.ChatAgentClientMapper;
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
class AgentChatAtmssoxClientTest {

    private AgentChatAtmssoxClient agentChatAtmssoxClient;

    @Mock
    private AgentChatApi agentChatApi;
    @Mock
    private ChatAgentClientMapper chatAgentClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentChatAtmssoxClient = new AgentChatAtmssoxClient(this.agentChatApi, this.chatAgentClientMapper, this.atmssoxClientCallExecutor);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentChatApi, this.chatAgentClientMapper, this.atmssoxClientCallExecutor);
    }

    @Test
    void givenChatRequest_whenSubmitAgentChatExecution_thenReturnMappedResponse() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final ChatAgentRequestDTO requestDTO = mock(ChatAgentRequestDTO.class);
        final SubmitChatExecutionResponseDTO responseDTO = mock(SubmitChatExecutionResponseDTO.class);
        final SubmitChatExecutionResponse expected = mock(SubmitChatExecutionResponse.class);

        when(this.chatAgentClientMapper.asChatAgentRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(
                invocation -> ((Supplier<SubmitChatExecutionResponseDTO>) invocation.getArgument(0)).get()
        );
        when(this.agentChatApi.submitAgentChatExecutionByExecutionsPath(agentId, requestDTO, "idempotency-key")).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asSubmitChatExecutionResponse(responseDTO)).thenReturn(expected);

        //when
        final SubmitChatExecutionResponse actual = this.agentChatAtmssoxClient.submitAgentChatExecution(agentId, request, "idempotency-key");

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.chatAgentClientMapper).asChatAgentRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentChatApi).submitAgentChatExecutionByExecutionsPath(agentId, requestDTO, "idempotency-key");
        verify(this.chatAgentClientMapper).asSubmitChatExecutionResponse(responseDTO);
    }

    @Test
    void givenExecutionLookupRequest_whenGetAgentChatExecution_thenReturnMappedResponse() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final UUID executionId = UUID.fromString("08350ef7-b16d-4e7b-b481-dbd68f919f5d");
        final UUID conversationId = UUID.fromString("d6527d11-7f15-4f75-b3a2-674fb2209973");
        final ChatExecutionDTO responseDTO = mock(ChatExecutionDTO.class);
        final ChatExecution expected = mock(ChatExecution.class);

        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(
                invocation -> ((Supplier<ChatExecutionDTO>) invocation.getArgument(0)).get()
        );
        when(this.agentChatApi.getAgentChatExecution(agentId, executionId, conversationId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asChatExecution(responseDTO)).thenReturn(expected);

        //when
        final ChatExecution actual = this.agentChatAtmssoxClient.getAgentChatExecution(agentId, executionId, conversationId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentChatApi).getAgentChatExecution(agentId, executionId, conversationId);
        verify(this.chatAgentClientMapper).asChatExecution(responseDTO);
    }
}
