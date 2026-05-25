package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitConversationExecutionResponse;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubmitConversationExecutionImplTest {

    private SubmitConversationExecution submitConversationExecution;

    @Mock
    private AgentConversationClient agentConversationClient;

    @BeforeEach
    void setUp() {
        this.submitConversationExecution = new SubmitConversationExecutionImpl(this.agentConversationClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentConversationClient);
    }

    @Test
    void givenConversationExecutionRequest_whenExecute_thenReturnClientResponse() {
        //given
        final UUID conversationId = UUID.fromString("f7cb84ec-9a48-47a3-8185-9308f11dfce6");
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final SubmitConversationExecutionResponse response = mock(SubmitConversationExecutionResponse.class);
        when(this.agentConversationClient.submitConversationExecution(conversationId, request)).thenReturn(response);

        //when
        final SubmitConversationExecutionResponse actual = this.submitConversationExecution.execute(conversationId, request);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentConversationClient).submitConversationExecution(conversationId, request);
    }
}
