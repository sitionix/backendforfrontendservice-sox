package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentProjectsPageResponse;
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
class GetAgentProjectsImplTest {

    private GetAgentProjects getAgentProjects;

    @Mock
    private AgentClient agentClient;

    @BeforeEach
    void setUp() {
        this.getAgentProjects = new GetAgentProjectsImpl(this.agentClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentClient);
    }

    @Test
    void givenPageAndSize_whenExecute_thenReturnAgentProjectsPageResponse() {
        //given
        final AgentProjectsPageResponse response = mock(AgentProjectsPageResponse.class);
        when(this.agentClient.getAgentProjects(1, 30)).thenReturn(response);

        //when
        final AgentProjectsPageResponse actual = this.getAgentProjects.execute(1, 30);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentClient).getAgentProjects(1, 30);
    }
}
