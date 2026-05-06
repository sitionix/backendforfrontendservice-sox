package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.Agent;
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
class GetAgentImplTest {

    private GetAgent getAgent;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.getAgent = new GetAgentImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentId_whenExecute_thenReturnAgent() {
        //given
        final UUID agentId = UUID.fromString("ec80888f-f98a-4470-92ec-43850f39d43e");
        final Agent response = mock(Agent.class);
        when(this.agentClient.getAgent(agentId)).thenReturn(response);

        //when
        final Agent actual = this.getAgent.execute(agentId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).getAgent(agentId);
    }
}
