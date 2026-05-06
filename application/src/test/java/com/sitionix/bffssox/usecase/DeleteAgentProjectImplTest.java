package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectOperationsPort;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class DeleteAgentProjectImplTest {

    private DeleteAgentProject deleteAgentProject;

    @Mock private AgentProjectOperationsPort agentClient;

    @BeforeEach
    void setUp() {
        this.deleteAgentProject = new DeleteAgentProjectImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenProjectId_whenExecute_thenDeleteProject() {
        //given
        final UUID projectId = UUID.randomUUID();

        //when
        this.deleteAgentProject.execute(projectId);

        //then
        verify(this.agentClient).deleteAgentProject(projectId);
    }
}
