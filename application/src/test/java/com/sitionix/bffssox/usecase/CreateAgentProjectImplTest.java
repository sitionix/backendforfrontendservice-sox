package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectClient;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
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
class CreateAgentProjectImplTest {

    private CreateAgentProject createAgentProject;

    @Mock
    private AgentProjectClient agentClient;

    @BeforeEach
    void setUp() {
        this.createAgentProject = new CreateAgentProjectImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenCreateAgentProjectRequest_whenExecute_thenReturnAgentProject() {
        //given
        final CreateAgentProjectRequest request = mock(CreateAgentProjectRequest.class);
        final AgentProject response = mock(AgentProject.class);
        when(this.agentClient.createAgentProject(request)).thenReturn(response);

        //when
        final AgentProject actual = this.createAgentProject.execute(request);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).createAgentProject(request);
    }
}
