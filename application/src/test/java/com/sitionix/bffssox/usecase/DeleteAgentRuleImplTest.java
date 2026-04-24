package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.DeleteAgentRuleResponse;
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
class DeleteAgentRuleImplTest {

    private DeleteAgentRuleImpl deleteAgentRule;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.deleteAgentRule = new DeleteAgentRuleImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentAndRuleIds_whenExecute_thenDelegateToClient() {
        //given
        final UUID agentId = UUID.fromString("fbe8f7c0-03c7-457d-8652-9bf3f70f6a5f");
        final UUID ruleId = UUID.fromString("7614d8c0-98f3-4eb4-b812-c95b8caf5d1b");
        final DeleteAgentRuleResponse expected = mock(DeleteAgentRuleResponse.class);

        when(this.agentClient.deleteAgentRule(agentId, ruleId)).thenReturn(expected);

        //when
        final DeleteAgentRuleResponse actual = this.deleteAgentRule.execute(agentId, ruleId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).deleteAgentRule(agentId, ruleId);
    }
}
