package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.usecase.DeleteAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversations;
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
class AgentConversationControllerTest {

    private AgentConversationController agentConversationController;

    @Mock private ChatAgentApiMapper chatAgentApiMapper;
    @Mock private GetAgentConversations getAgentConversations;
    @Mock private GetAgentConversation getAgentConversation;
    @Mock private DeleteAgentConversation deleteAgentConversation;

    @BeforeEach
    void setUp() {
        this.agentConversationController = new AgentConversationController(this.chatAgentApiMapper, this.getAgentConversations,
                this.getAgentConversation, this.deleteAgentConversation);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.chatAgentApiMapper, this.getAgentConversations, this.getAgentConversation, this.deleteAgentConversation);
    }

    @Test
    void givenConversationId_whenDeleteAgentConversation_thenReturnNoContent() {
        //given
        final UUID conversationId = UUID.fromString("4dd6a86d-7de1-4187-9624-fc912633b102");

        //when
        final ResponseEntity<Void> actual = this.agentConversationController.deleteAgentConversation(conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.noContent().build());
        verify(this.deleteAgentConversation).execute(conversationId);
    }

    @Test
    void givenAgentId_whenGetAgentConversations_thenReturnMappedResponse() {
        //given
        final UUID agentId = UUID.randomUUID();
        final AgentConversationsResponse response = mock(AgentConversationsResponse.class);
        final AgentConversationsResponseDTO responseDto = mock(AgentConversationsResponseDTO.class);
        when(this.getAgentConversations.execute(agentId)).thenReturn(response);
        when(this.chatAgentApiMapper.asAgentConversationsResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentConversationsResponseDTO> actual = this.agentConversationController.getAgentConversations(agentId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentConversations).execute(agentId);
        verify(this.chatAgentApiMapper).asAgentConversationsResponseDto(response);
    }

    @Test
    void givenConversationId_whenGetAgentConversation_thenReturnMappedResponse() {
        //given
        final UUID conversationId = UUID.randomUUID();
        final AgentConversationDetails response = mock(AgentConversationDetails.class);
        final AgentConversationDetailsDTO responseDto = mock(AgentConversationDetailsDTO.class);
        when(this.getAgentConversation.execute(conversationId)).thenReturn(response);
        when(this.chatAgentApiMapper.asAgentConversationDetailsDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentConversationDetailsDTO> actual = this.agentConversationController.getAgentConversation(conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentConversation).execute(conversationId);
        verify(this.chatAgentApiMapper).asAgentConversationDetailsDto(response);
    }
}
