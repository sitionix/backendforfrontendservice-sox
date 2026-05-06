package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentOperationsPort;
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
class ArchiveAgentImplTest {

    private ArchiveAgent archiveAgent;

    @Mock
    private AgentOperationsPort agentClient;

    @BeforeEach
    void setUp() {
        this.archiveAgent = new ArchiveAgentImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentId_whenExecute_thenReturnAgent() {
        //given
        final UUID agentId = UUID.fromString("00000000-0000-0000-0000-000000000008");
        final Agent response = mock(Agent.class);
        when(this.agentClient.archiveAgent(agentId)).thenReturn(response);

        //when
        final Agent actual = this.archiveAgent.execute(agentId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).archiveAgent(agentId);
    }
}
