package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentConversationDetails;
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
class GetAgentConversationImplTest {

    private GetAgentConversation getAgentConversation;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.getAgentConversation = new GetAgentConversationImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentIdAndConversationId_whenExecute_thenReturnAgentConversationDetails() {
        //given
        final UUID agentId = UUID.fromString("21111111-1111-1111-1111-111111111111");
        final UUID conversationId = UUID.fromString("31111111-1111-1111-1111-111111111111");
        final AgentConversationDetails response = mock(AgentConversationDetails.class);
        when(this.agentClient.getAgentConversation(agentId, conversationId)).thenReturn(response);

        //when
        final AgentConversationDetails actual = this.getAgentConversation.execute(agentId, conversationId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).getAgentConversation(agentId, conversationId);
    }
}
