package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
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
class SubmitAgentChatExecutionImplTest {

    private SubmitAgentChatExecution submitAgentChatExecution;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.submitAgentChatExecution = new SubmitAgentChatExecutionImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenSubmitChatExecutionRequest_whenExecute_thenReturnClientResponse() {
        //given
        final UUID agentId = UUID.fromString("11ea96d9-7dc2-4810-a233-c7f8d48ece29");
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final SubmitChatExecutionResponse response = mock(SubmitChatExecutionResponse.class);

        when(this.agentClient.submitAgentChatExecution(agentId, request, "key")).thenReturn(response);

        //when
        final SubmitChatExecutionResponse actual = this.submitAgentChatExecution.execute(agentId, request, "key");

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).submitAgentChatExecution(agentId, request, "key");
    }
}
