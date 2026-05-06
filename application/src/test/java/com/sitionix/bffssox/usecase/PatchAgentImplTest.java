package com.sitionix.bffssox.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.sitionix.bffssox.client.AgentOperationsPort;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PatchAgentImplTest {

    private PatchAgent patchAgent;

    @Mock
    private AgentOperationsPort agentClient;

    @BeforeEach
    void setUp() {
        this.patchAgent = new PatchAgentImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenPatchAgentRequest_whenExecute_thenReturnAgent() {
        //given
        final UUID givenAgentId = UUID.fromString("ebac37f0-90a2-4f6b-ab99-73f6ac5cf675");
        final PatchAgentRequest givenRequest = mock(PatchAgentRequest.class);
        final Agent expected = mock(Agent.class);
        when(this.agentClient.patchAgent(givenAgentId, givenRequest)).thenReturn(expected);

        //when
        final Agent actual = this.patchAgent.execute(givenAgentId, givenRequest);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).patchAgent(givenAgentId, givenRequest);
    }
}

