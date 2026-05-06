package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentRuleClient;
import com.sitionix.bffssox.domain.AgentRule;
import com.sitionix.bffssox.domain.CreateAgentRuleRequest;
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
class CreateAgentRuleImplTest {

    private CreateAgentRuleImpl createAgentRule;

    @Mock
    private AgentRuleClient agentClient;

    @BeforeEach
    void setUp() {
        this.createAgentRule = new CreateAgentRuleImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenAgentIdAndRequest_whenExecute_thenDelegateToClient() {
        //given
        final UUID agentId = UUID.fromString("18c1efa6-7342-46fd-b744-39ac0fc72167");
        final CreateAgentRuleRequest request = mock(CreateAgentRuleRequest.class);
        final AgentRule expected = mock(AgentRule.class);

        when(this.agentClient.createAgentRule(agentId, request)).thenReturn(expected);

        //when
        final AgentRule actual = this.createAgentRule.execute(agentId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).createAgentRule(agentId, request);
    }
}
