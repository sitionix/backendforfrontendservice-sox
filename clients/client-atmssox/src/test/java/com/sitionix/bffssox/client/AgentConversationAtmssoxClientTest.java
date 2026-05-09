package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentConversationApi;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateProjectConversationRequestDTO;
import com.app_afesox.atmssox.client.dto.ProjectConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.ProjectConversationsResponseDTO;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import com.sitionix.bffssox.mapper.ChatAgentClientMapper;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentConversationAtmssoxClientTest {

    private AgentConversationAtmssoxClient agentConversationAtmssoxClient;

    @Mock
    private AgentConversationApi agentConversationApi;
    @Mock
    private ChatAgentClientMapper chatAgentClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentConversationAtmssoxClient = new AgentConversationAtmssoxClient(
                this.agentConversationApi,
                this.chatAgentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentConversationApi, this.chatAgentClientMapper, this.atmssoxClientCallExecutor);
    }

    @Test
    void givenAgentId_whenGetAgentConversations_thenReturnMappedResponse() {
        //given
        final UUID agentId = UUID.fromString("3064ed14-b2ab-4c37-a264-94574beb8dd2");
        final AgentConversationsResponseDTO responseDTO = mock(AgentConversationsResponseDTO.class);
        final AgentConversationsResponse expected = mock(AgentConversationsResponse.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentConversationsResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentConversationApi.getAgentConversations(agentId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asAgentConversationsResponse(responseDTO)).thenReturn(expected);

        //when
        final AgentConversationsResponse actual = this.agentConversationAtmssoxClient.getAgentConversations(agentId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentConversationApi).getAgentConversations(agentId);
        verify(this.chatAgentClientMapper).asAgentConversationsResponse(responseDTO);
    }

    @Test
    void givenConversationId_whenGetAgentConversation_thenReturnMappedDetails() {
        //given
        final UUID conversationId = UUID.randomUUID();
        final AgentConversationDetailsDTO responseDTO = mock(AgentConversationDetailsDTO.class);
        final AgentConversationDetails expected = mock(AgentConversationDetails.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentConversationDetailsDTO>) invocation.getArgument(0)).get());
        when(this.agentConversationApi.getAgentConversation(conversationId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asAgentConversationDetails(responseDTO)).thenReturn(expected);

        //when
        final AgentConversationDetails actual = this.agentConversationAtmssoxClient.getAgentConversation(conversationId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentConversationApi).getAgentConversation(conversationId);
        verify(this.chatAgentClientMapper).asAgentConversationDetails(responseDTO);
    }

    @Test
    void givenConversationId_whenDeleteAgentConversation_thenDelegateToApi() {
        //given
        final UUID conversationId = UUID.randomUUID();
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<Object>) invocation.getArgument(0)).get());

        //when
        this.agentConversationAtmssoxClient.deleteAgentConversation(conversationId);

        //then
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentConversationApi).deleteAgentConversation(conversationId);
    }

    @Test
    void givenProjectRequest_whenCreateProjectConversation_thenReturnMappedDetails() {
        //given
        final UUID projectId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final CreateProjectConversationRequest request = mock(CreateProjectConversationRequest.class);
        final CreateProjectConversationRequestDTO requestDTO = mock(CreateProjectConversationRequestDTO.class);
        final ProjectConversationDetailsDTO responseDTO = mock(ProjectConversationDetailsDTO.class);
        final ProjectConversationDetails expected = mock(ProjectConversationDetails.class);
        when(this.chatAgentClientMapper.asCreateProjectConversationRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<ProjectConversationDetailsDTO>) invocation.getArgument(0)).get());
        when(this.agentConversationApi.createProjectConversation(projectId, requestDTO)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asProjectConversationDetails(responseDTO)).thenReturn(expected);

        //when
        final ProjectConversationDetails actual = this.agentConversationAtmssoxClient.createProjectConversation(projectId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.chatAgentClientMapper).asCreateProjectConversationRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentConversationApi).createProjectConversation(projectId, requestDTO);
        verify(this.chatAgentClientMapper).asProjectConversationDetails(responseDTO);
    }

    @Test
    void givenProjectId_whenListProjectConversations_thenReturnMappedResponse() {
        //given
        final UUID projectId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final ProjectConversationsResponseDTO responseDTO = mock(ProjectConversationsResponseDTO.class);
        final ProjectConversationsResponse expected = mock(ProjectConversationsResponse.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<ProjectConversationsResponseDTO>) invocation.getArgument(0)).get());
        when(this.agentConversationApi.listProjectConversations(projectId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asProjectConversationsResponse(responseDTO)).thenReturn(expected);

        //when
        final ProjectConversationsResponse actual = this.agentConversationAtmssoxClient.listProjectConversations(projectId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentConversationApi).listProjectConversations(projectId);
        verify(this.chatAgentClientMapper).asProjectConversationsResponse(responseDTO);
    }

    @Test
    void givenProjectAndConversationId_whenGetProjectConversation_thenReturnMappedDetails() {
        //given
        final UUID projectId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        final UUID conversationId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        final ProjectConversationDetailsDTO responseDTO = mock(ProjectConversationDetailsDTO.class);
        final ProjectConversationDetails expected = mock(ProjectConversationDetails.class);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<ProjectConversationDetailsDTO>) invocation.getArgument(0)).get());
        when(this.agentConversationApi.getProjectConversation(projectId, conversationId)).thenReturn(responseDTO);
        when(this.chatAgentClientMapper.asProjectConversationDetails(responseDTO)).thenReturn(expected);

        //when
        final ProjectConversationDetails actual = this.agentConversationAtmssoxClient.getProjectConversation(projectId, conversationId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentConversationApi).getProjectConversation(projectId, conversationId);
        verify(this.chatAgentClientMapper).asProjectConversationDetails(responseDTO);
    }
}
