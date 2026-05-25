package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateProjectConversationRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitConversationExecutionRequestDTO;
import com.app_afesox.bffssox.api_first.dto.SubmitConversationExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import com.sitionix.bffssox.domain.SubmitConversationExecutionResponse;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.usecase.DeleteAgentConversation;
import com.sitionix.bffssox.usecase.CreateProjectConversation;
import com.sitionix.bffssox.usecase.ListProjectConversations;
import com.sitionix.bffssox.usecase.GetProjectConversation;
import com.sitionix.bffssox.usecase.SubmitConversationExecution;
import com.sitionix.bffssox.usecase.GetAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversations;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentConversationControllerTest {

    private AgentConversationController agentConversationController;

    @Mock private ChatAgentApiMapper chatAgentApiMapper;
    @Mock private GetAgentConversations getAgentConversations;
    @Mock private GetAgentConversation getAgentConversation;
    @Mock private DeleteAgentConversation deleteAgentConversation;
    @Mock private CreateProjectConversation createProjectConversation;
    @Mock private ListProjectConversations listProjectConversations;
    @Mock private GetProjectConversation getProjectConversation;
    @Mock private SubmitConversationExecution submitConversationExecution;

    @BeforeEach
    void setUp() {
        this.agentConversationController = new AgentConversationController(
                this.chatAgentApiMapper,
                this.getAgentConversations,
                this.getAgentConversation,
                this.deleteAgentConversation,
                this.createProjectConversation,
                this.listProjectConversations,
                this.getProjectConversation,
                this.submitConversationExecution
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.chatAgentApiMapper,
                this.getAgentConversations,
                this.getAgentConversation,
                this.deleteAgentConversation,
                this.createProjectConversation,
                this.listProjectConversations,
                this.getProjectConversation,
                this.submitConversationExecution
        );
    }

    @Test
    void givenConversationId_whenDeleteAgentConversation_thenReturnNoContent() {
        //given
        final UUID conversationId = UUID.fromString("4dd6a86d-7de1-4187-9624-fc912633b102");

        //when
        final ResponseEntity<Void> actual = this.agentConversationController.deleteAgentConversation(conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.noContent().build());
        verify(this.deleteAgentConversation).execute(conversationId);
    }

    @Test
    void givenAgentId_whenGetAgentConversations_thenReturnMappedResponse() {
        //given
        final UUID agentId = UUID.randomUUID();
        final AgentConversationsResponse response = mock(AgentConversationsResponse.class);
        final AgentConversationsResponseDTO responseDto = mock(AgentConversationsResponseDTO.class);
        when(this.getAgentConversations.execute(agentId)).thenReturn(response);
        when(this.chatAgentApiMapper.asAgentConversationsResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentConversationsResponseDTO> actual = this.agentConversationController.getAgentConversations(agentId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentConversations).execute(agentId);
        verify(this.chatAgentApiMapper).asAgentConversationsResponseDto(response);
    }

    @Test
    void givenConversationId_whenGetAgentConversation_thenReturnMappedResponse() {
        //given
        final UUID conversationId = UUID.randomUUID();
        final AgentConversationDetails response = mock(AgentConversationDetails.class);
        final AgentConversationDetailsDTO responseDto = mock(AgentConversationDetailsDTO.class);
        when(this.getAgentConversation.execute(conversationId)).thenReturn(response);
        when(this.chatAgentApiMapper.asAgentConversationDetailsDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentConversationDetailsDTO> actual = this.agentConversationController.getAgentConversation(conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getAgentConversation).execute(conversationId);
        verify(this.chatAgentApiMapper).asAgentConversationDetailsDto(response);
    }

    @Test
    void givenProjectIdAndRequest_whenCreateProjectConversation_thenReturnCreatedResponse() {
        //given
        final UUID projectId = UUID.randomUUID();
        final CreateProjectConversationRequestDTO requestDto = mock(CreateProjectConversationRequestDTO.class);
        final CreateProjectConversationRequest request = mock(CreateProjectConversationRequest.class);
        final ProjectConversationDetails response = mock(ProjectConversationDetails.class);
        final ProjectConversationDetailsDTO responseDto = mock(ProjectConversationDetailsDTO.class);
        when(this.chatAgentApiMapper.asCreateProjectConversationRequest(requestDto)).thenReturn(request);
        when(this.createProjectConversation.execute(projectId, request)).thenReturn(response);
        when(this.chatAgentApiMapper.asProjectConversationDetailsDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<ProjectConversationDetailsDTO> actual =
                this.agentConversationController.createProjectConversation(projectId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(201).body(responseDto));
        verify(this.chatAgentApiMapper).asCreateProjectConversationRequest(requestDto);
        verify(this.createProjectConversation).execute(projectId, request);
        verify(this.chatAgentApiMapper).asProjectConversationDetailsDto(response);
    }

    @Test
    void givenProjectId_whenListProjectConversations_thenReturnMappedResponse() {
        //given
        final UUID projectId = UUID.randomUUID();
        final ProjectConversationsResponse response = mock(ProjectConversationsResponse.class);
        final ProjectConversationsResponseDTO responseDto = mock(ProjectConversationsResponseDTO.class);
        when(this.listProjectConversations.execute(projectId)).thenReturn(response);
        when(this.chatAgentApiMapper.asProjectConversationsResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<ProjectConversationsResponseDTO> actual =
                this.agentConversationController.listProjectConversations(projectId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.listProjectConversations).execute(projectId);
        verify(this.chatAgentApiMapper).asProjectConversationsResponseDto(response);
    }

    @Test
    void givenProjectIdAndConversationId_whenGetProjectConversation_thenReturnMappedResponse() {
        //given
        final UUID projectId = UUID.randomUUID();
        final UUID conversationId = UUID.randomUUID();
        final ProjectConversationDetails response = mock(ProjectConversationDetails.class);
        final ProjectConversationDetailsDTO responseDto = mock(ProjectConversationDetailsDTO.class);
        when(this.getProjectConversation.execute(projectId, conversationId)).thenReturn(response);
        when(this.chatAgentApiMapper.asProjectConversationDetailsDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<ProjectConversationDetailsDTO> actual =
                this.agentConversationController.getProjectConversation(projectId, conversationId);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDto));
        verify(this.getProjectConversation).execute(projectId, conversationId);
        verify(this.chatAgentApiMapper).asProjectConversationDetailsDto(response);
    }

    @Test
    void givenConversationIdAndRequest_whenSubmitConversationExecution_thenReturnAcceptedResponse() {
        //given
        final UUID conversationId = UUID.randomUUID();
        final SubmitConversationExecutionRequestDTO requestDto = mock(SubmitConversationExecutionRequestDTO.class);
        final ChatAgentRequest request = mock(ChatAgentRequest.class);
        final SubmitConversationExecutionResponse response = mock(SubmitConversationExecutionResponse.class);
        final SubmitConversationExecutionResponseDTO responseDto = mock(SubmitConversationExecutionResponseDTO.class);
        when(this.chatAgentApiMapper.asChatAgentRequest(requestDto)).thenReturn(request);
        when(this.submitConversationExecution.execute(conversationId, request)).thenReturn(response);
        when(this.chatAgentApiMapper.asSubmitConversationExecutionResponseDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<SubmitConversationExecutionResponseDTO> actual =
                this.agentConversationController.submitConversationExecution(conversationId, requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.accepted().body(responseDto));
        verify(this.chatAgentApiMapper).asChatAgentRequest(requestDto);
        verify(this.submitConversationExecution).execute(conversationId, request);
        verify(this.chatAgentApiMapper).asSubmitConversationExecutionResponseDto(response);
    }
}
