package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.usecase.GetAgentChatExecution;
import com.sitionix.bffssox.usecase.SubmitAgentChatExecution;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentChatControllerTest {

    private AgentChatController agentChatController;

    @Mock private ChatAgentApiMapper chatAgentApiMapper;
    @Mock private SubmitAgentChatExecution submitAgentChatExecution;
    @Mock private GetAgentChatExecution getAgentChatExecution;

    @BeforeEach
    void setUp() {
        this.agentChatController = new AgentChatController(this.chatAgentApiMapper, this.submitAgentChatExecution, this.getAgentChatExecution);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.chatAgentApiMapper, this.submitAgentChatExecution, this.getAgentChatExecution);
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
        final ResponseEntity<SubmitChatExecutionResponseDTO> actual = this.agentChatController.submitAgentChatExecution(agentId, requestDto, "idem");

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
        final ResponseEntity<ChatExecutionDTO> actual = this.agentChatController.getAgentChatExecution(agentId, executionId, conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentChatExecution).execute(agentId, executionId, conversationId);
        verify(this.chatAgentApiMapper).asChatExecutionDto(response);
    }
}
