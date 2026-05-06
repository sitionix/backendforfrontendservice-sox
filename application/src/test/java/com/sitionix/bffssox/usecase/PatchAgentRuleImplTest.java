package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentRuleOperationsPort;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.PatchAgentRuleRequest;
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
class PatchAgentRuleImplTest {

    private PatchAgentRuleImpl patchAgentRule;

    @Mock
    private AgentRuleOperationsPort agentClient;

    @BeforeEach
    void setUp() {
        this.patchAgentRule = new PatchAgentRuleImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentAndRuleIdsAndRequest_whenExecute_thenDelegateToClient() {
        //given
        final UUID agentId = UUID.fromString("30655c24-43f7-44d2-9fd0-d2f2d935fce6");
        final UUID ruleId = UUID.fromString("8fb5ac40-1599-44d8-809a-9471136f30b2");
        final PatchAgentRuleRequest request = mock(PatchAgentRuleRequest.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.agentClient.patchAgentRule(agentId, ruleId, request)).thenReturn(expected);

        //when
        final AgentRule actual = this.patchAgentRule.execute(agentId, ruleId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).patchAgentRule(agentId, ruleId, request);
    }
}
