package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentRuleOperationsPort;
import com.sitionix.bffssox.domain.AgentRulesResponse;
import com.sitionix.bffssox.domain.GetAgentRulesQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAgentRulesImplTest {

    private GetAgentRulesImpl getAgentRules;

    @Mock
    private AgentRuleOperationsPort agentClient;

    @BeforeEach
    void setUp() {
        this.getAgentRules = new GetAgentRulesImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentId_whenExecute_thenDelegateToClient() {
        //given
        final UUID agentId = UUID.fromString("7e31eb53-58bb-4d8d-ac81-f71abecdfb4c");
        final GetAgentRulesQuery query = new GetAgentRulesQuery("ACTIVE", null);
        final AgentRulesResponse expected = mock(AgentRulesResponse.class);

        when(this.agentClient.getAgentRules(agentId, query)).thenReturn(expected);

        //when
        final AgentRulesResponse actual = this.getAgentRules.execute(agentId, query);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).getAgentRules(agentId, query);
    }
}
