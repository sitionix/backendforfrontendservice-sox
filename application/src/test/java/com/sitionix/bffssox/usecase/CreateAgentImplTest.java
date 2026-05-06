package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.CreateAgentRequest;
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
class CreateAgentImplTest {

    private CreateAgent createAgent;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.createAgent = new CreateAgentImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenCreateAgentRequest_whenExecute_thenReturnAgent() {
        //given
        final CreateAgentRequest request = mock(CreateAgentRequest.class);
        final Agent response = mock(Agent.class);
        when(this.agentClient.createAgent(request)).thenReturn(response);

        //when
        final Agent actual = this.createAgent.execute(request);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).createAgent(request);
    }
}
