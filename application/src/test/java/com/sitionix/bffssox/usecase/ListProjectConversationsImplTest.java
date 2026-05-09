package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
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
class ListProjectConversationsImplTest {

    private ListProjectConversations listProjectConversations;

    @Mock
    private AgentConversationClient agentConversationClient;

    @BeforeEach
    void setUp() {
        this.listProjectConversations = new ListProjectConversationsImpl(this.agentConversationClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentConversationClient);
    }

    @Test
    void givenProjectId_whenExecute_thenReturnProjectConversationsResponse() {
        //given
        final UUID projectId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final ProjectConversationsResponse response = mock(ProjectConversationsResponse.class);
        when(this.agentConversationClient.listProjectConversations(projectId)).thenReturn(response);

        //when
        final ProjectConversationsResponse actual = this.listProjectConversations.execute(projectId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentConversationClient).listProjectConversations(projectId);
    }
}
