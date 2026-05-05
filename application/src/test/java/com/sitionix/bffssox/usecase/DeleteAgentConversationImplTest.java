package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class DeleteAgentConversationImplTest {

    private DeleteAgentConversation deleteAgentConversation;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.deleteAgentConversation = new DeleteAgentConversationImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenConversationId_whenExecute_thenDeleteConversationInClient() {
        //given
        final UUID conversationId = UUID.fromString("7a0f41f6-0664-45bb-944d-30de1120f3f1");

        //when
        this.deleteAgentConversation.execute(conversationId);

        //then
        verify(this.agentClient).deleteAgentConversation(conversationId);
    }
}
