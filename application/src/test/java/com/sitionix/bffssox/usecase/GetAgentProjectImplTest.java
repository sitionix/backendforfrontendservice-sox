package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentProject;
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
class GetAgentProjectImplTest {

    private GetAgentProject getAgentProject;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.getAgentProject = new GetAgentProjectImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenProjectId_whenExecute_thenReturnProject() {
        //given
        final UUID projectId = UUID.fromString("7da63ca3-ca1f-4ec2-b050-8e46f5a3ad4f");
        final AgentProject expected = mock(AgentProject.class);
        when(this.agentClient.getAgentProject(projectId)).thenReturn(expected);

        //when
        final AgentProject actual = this.getAgentProject.execute(projectId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).getAgentProject(projectId);
    }
}
