package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentChatClient;
import com.sitionix.bffssox.domain.ChatExecution;
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
class GetAgentChatExecutionImplTest {

    private GetAgentChatExecution getAgentChatExecution;

    @Mock
    private AgentChatClient agentClient;

    @BeforeEach
    void setUp() {
        this.getAgentChatExecution = new GetAgentChatExecutionImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenExecutionLookupRequest_whenExecute_thenReturnClientResponse() {
        //given
        final UUID agentId = UUID.fromString("11ea96d9-7dc2-4810-a233-c7f8d48ece29");
        final UUID executionId = UUID.fromString("9fcb7f34-1f0f-47f9-bdb5-607f9f4cc57f");
        final UUID conversationId = UUID.fromString("e0887f26-f37c-4e79-a7a7-905196f9a056");
        final ChatExecution response = mock(ChatExecution.class);

        when(this.agentClient.getAgentChatExecution(agentId, executionId, conversationId)).thenReturn(response);

        //when
        final ChatExecution actual = this.getAgentChatExecution.execute(agentId, executionId, conversationId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).getAgentChatExecution(agentId, executionId, conversationId);
    }
}
