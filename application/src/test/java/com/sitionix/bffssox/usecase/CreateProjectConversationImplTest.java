package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
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
class CreateProjectConversationImplTest {

    private CreateProjectConversation createProjectConversation;

    @Mock
    private AgentConversationClient agentConversationClient;

    @BeforeEach
    void setUp() {
        this.createProjectConversation = new CreateProjectConversationImpl(this.agentConversationClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentConversationClient);
    }

    @Test
    void givenProjectIdAndRequest_whenExecute_thenReturnProjectConversationDetails() {
        //given
        final UUID projectId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final CreateProjectConversationRequest request = mock(CreateProjectConversationRequest.class);
        final ProjectConversationDetails response = mock(ProjectConversationDetails.class);
        when(this.agentConversationClient.createProjectConversation(projectId, request)).thenReturn(response);

        //when
        final ProjectConversationDetails actual = this.createProjectConversation.execute(projectId, request);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.agentConversationClient).createProjectConversation(projectId, request);
    }
}
