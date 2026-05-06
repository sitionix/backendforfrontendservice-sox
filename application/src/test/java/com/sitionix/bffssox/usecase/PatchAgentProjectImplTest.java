package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
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
class PatchAgentProjectImplTest {

    private PatchAgentProject patchAgentProject;

    @Mock private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.patchAgentProject = new PatchAgentProjectImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenProjectIdAndRequest_whenExecute_thenReturnAgentProject() {
        //given
        final UUID projectId = UUID.randomUUID();
        final PatchAgentProjectRequest request = mock(PatchAgentProjectRequest.class);
        final AgentProject expected = mock(AgentProject.class);
        when(this.agentClient.patchAgentProject(projectId, request)).thenReturn(expected);

        //when
        final AgentProject actual = this.patchAgentProject.execute(projectId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.agentClient).patchAgentProject(projectId, request);
    }
}
