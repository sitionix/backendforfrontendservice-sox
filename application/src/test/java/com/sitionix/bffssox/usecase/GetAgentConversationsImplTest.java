package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
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
class GetAgentConversationsImplTest {

    private GetAgentConversations getAgentConversations;

    @Mock
    private AgentConversationClient agentClient;

    @BeforeEach
    void setUp() {
        this.getAgentConversations = new GetAgentConversationsImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentId_whenExecute_thenReturnAgentConversationsResponse() {
        //given
        final UUID agentId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final AgentConversationsResponse response = mock(AgentConversationsResponse.class);
        when(this.agentClient.getAgentConversations(agentId)).thenReturn(response);

        //when
        final AgentConversationsResponse actual = this.getAgentConversations.execute(agentId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).getAgentConversations(agentId);
    }
}
