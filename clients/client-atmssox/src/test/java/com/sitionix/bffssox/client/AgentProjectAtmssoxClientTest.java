package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentProjectApi;
import com.app_afesox.atmssox.client.dto.AgentProjectDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentProjectRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentProjectClientMapper;
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
class AgentProjectAtmssoxClientTest {

    private AgentProjectAtmssoxClient agentProjectAtmssoxClient;

    @Mock
    private AgentProjectApi agentProjectApi;
    @Mock
    private CreateAgentProjectClientMapper createAgentProjectClientMapper;
    @Mock
    private PatchAgentProjectClientMapper patchAgentProjectClientMapper;
    @Mock
    private AgentClientMapper agentClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentProjectAtmssoxClient = new AgentProjectAtmssoxClient(
                this.agentProjectApi,
                this.createAgentProjectClientMapper,
                this.patchAgentProjectClientMapper,
                this.agentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentProjectApi, this.createAgentProjectClientMapper, this.patchAgentProjectClientMapper,
                this.agentClientMapper, this.atmssoxClientCallExecutor);
    }

    @Test
    void givenCreateProjectRequest_whenCreateAgentProject_thenReturnMappedProject() {
        //given
        final CreateAgentProjectRequest request = mock(CreateAgentProjectRequest.class);
        final CreateAgentProjectRequestDTO requestDTO = mock(CreateAgentProjectRequestDTO.class);
        final AgentProjectDTO responseDTO = mock(AgentProjectDTO.class);
        final AgentProject expected = mock(AgentProject.class);
        when(this.createAgentProjectClientMapper.asCreateAgentProjectRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentProjectDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.createAgentProject(requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentProject(responseDTO)).thenReturn(expected);

        //when
        final AgentProject actual = this.agentProjectAtmssoxClient.createAgentProject(request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.createAgentProjectClientMapper).asCreateAgentProjectRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).createAgentProject(requestDTO);
        verify(this.agentClientMapper).asAgentProject(responseDTO);
    }

    @Test
    void givenPatchProjectRequest_whenPatchAgentProject_thenReturnMappedProject() {
        //given
        final UUID projectId = UUID.randomUUID();
        final PatchAgentProjectRequest request = mock(PatchAgentProjectRequest.class);
        final PatchAgentProjectRequestDTO requestDTO = mock(PatchAgentProjectRequestDTO.class);
        final AgentProjectDTO responseDTO = mock(AgentProjectDTO.class);
        final AgentProject expected = mock(AgentProject.class);
        when(this.patchAgentProjectClientMapper.asPatchAgentProjectRequestDto(request)).thenReturn(requestDTO);
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<AgentProjectDTO>) invocation.getArgument(0)).get());
        when(this.agentProjectApi.patchAgentProject(projectId, requestDTO)).thenReturn(responseDTO);
        when(this.agentClientMapper.asAgentProject(responseDTO)).thenReturn(expected);

        //when
        final AgentProject actual = this.agentProjectAtmssoxClient.patchAgentProject(projectId, request);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.patchAgentProjectClientMapper).asPatchAgentProjectRequestDto(request);
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).patchAgentProject(projectId, requestDTO);
        verify(this.agentClientMapper).asAgentProject(responseDTO);
    }

    @Test
    void givenProjectId_whenDeleteAgentProject_thenCallDeleteApi() {
        //given
        final UUID projectId = UUID.randomUUID();
        when(this.atmssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> ((Supplier<Void>) invocation.getArgument(0)).get());

        //when
        this.agentProjectAtmssoxClient.deleteAgentProject(projectId);

        //then
        verify(this.atmssoxClientCallExecutor).execute(any());
        verify(this.agentProjectApi).deleteAgentProject(projectId);
    }
}
