package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentOperationsPort;
import com.sitionix.bffssox.domain.AgentsResponse;
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
class GetAgentsImplTest {

    private GetAgents getAgents;

    @Mock
    private AgentOperationsPort agentClient;

    @BeforeEach
    void setUp() {
        this.getAgents = new GetAgentsImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenGetAgentsRequest_whenExecute_thenReturnAgentsResponse() {
        //given
        final AgentsResponse response = mock(AgentsResponse.class);
        when(this.agentClient.getAgents()).thenReturn(response);

        //when
        final AgentsResponse actual = this.getAgents.execute();

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).getAgents();
    }
}
