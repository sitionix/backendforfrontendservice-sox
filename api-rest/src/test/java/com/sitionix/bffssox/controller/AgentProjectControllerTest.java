package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.AgentProjectDTO;
import com.app_afesox.bffssox.api_first.dto.CreateAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.mapper.AgentApiMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectApiMapper;
import com.sitionix.bffssox.usecase.CreateAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProject;
import com.sitionix.bffssox.usecase.GetAgentProjects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentProjectControllerTest {

    private AgentProjectController agentProjectController;

    @Mock private CreateAgentProjectApiMapper createAgentProjectApiMapper;
    @Mock private AgentApiMapper agentApiMapper;
    @Mock private CreateAgentProject createAgentProject;
    @Mock private GetAgentProjects getAgentProjects;
    @Mock private GetAgentProject getAgentProject;

    @BeforeEach
    void setUp() {
        this.agentProjectController = new AgentProjectController(this.createAgentProjectApiMapper, this.agentApiMapper,
                this.createAgentProject, this.getAgentProjects, this.getAgentProject);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.createAgentProjectApiMapper, this.agentApiMapper, this.createAgentProject, this.getAgentProjects, this.getAgentProject);
    }

    @Test
    void givenCreateAgentProjectRequestDto_whenCreateAgentProject_thenReturnCreatedResponse() {
        //given
        final CreateAgentProjectRequestDTO requestDto = mock(CreateAgentProjectRequestDTO.class);
        final CreateAgentProjectRequest request = mock(CreateAgentProjectRequest.class);
        final AgentProject response = mock(AgentProject.class);
        final AgentProjectDTO responseDto = mock(AgentProjectDTO.class);
        when(this.createAgentProjectApiMapper.asCreateAgentProjectRequest(requestDto)).thenReturn(request);
        when(this.createAgentProject.execute(request)).thenReturn(response);
        when(this.agentApiMapper.asAgentProjectDto(response)).thenReturn(responseDto);

        //when
        final ResponseEntity<AgentProjectDTO> actual = this.agentProjectController.createAgentProject(requestDto);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(responseDto));
        verify(this.createAgentProjectApiMapper).asCreateAgentProjectRequest(requestDto);
        verify(this.createAgentProject).execute(request);
        verify(this.agentApiMapper).asAgentProjectDto(response);
    }
}
