package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
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
class ChatAgentImplTest {

    private ChatAgent chatAgent;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.chatAgent = new ChatAgentImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenChatAgentRequest_whenExecute_thenReturnChatAgentResponse() {
        //given
        final UUID agentId = UUID.fromString("2679f99c-1700-41d5-aa20-d58dfdf15a5f");
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final ChatAgentResponse response = mock(ChatAgentResponse.class);
        when(this.agentClient.chatAgent(agentId, request)).thenReturn(response);

        //when
        final ChatAgentResponse actual = this.chatAgent.execute(agentId, request);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).chatAgent(agentId, request);
    }
}
