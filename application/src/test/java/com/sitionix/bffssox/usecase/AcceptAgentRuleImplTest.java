package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentRuleOperationsPort;
import com.sitionix.bffssox.domain.AcceptAgentRuleRequest;
import com.sitionix.bffssox.domain.AgentRule;
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
class AcceptAgentRuleImplTest {

    private AcceptAgentRuleImpl acceptAgentRule;

    @Mock
    private AgentRuleOperationsPort agentClient;

    @BeforeEach
    void setUp() {
        this.acceptAgentRule = new AcceptAgentRuleImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentIdAndRuleIdAndRequest_whenExecute_thenDelegateToClient() {
        //given
        final UUID agentId = UUID.fromString("4f7cbfcb-1d84-4463-ad97-a8387d4eaaf6");
        final UUID ruleId = UUID.fromString("89bb4ed2-4c9b-412d-8a68-f4c6f6ec489a");
        final AcceptAgentRuleRequest request = mock(AcceptAgentRuleRequest.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.agentClient.acceptAgentRule(agentId, ruleId, request)).thenReturn(expected);

        //when
        final AgentRule actual = this.acceptAgentRule.execute(agentId, ruleId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).acceptAgentRule(agentId, ruleId, request);
    }
}
