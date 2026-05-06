package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentProjectApi;
import com.app_afesox.atmssox.client.dto.AgentProjectDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentProjectClientMapper;
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
    private AgentClientMapper agentClientMapper;
    @Mock
    private AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.agentProjectAtmssoxClient = new AgentProjectAtmssoxClient(
                this.agentProjectApi,
                this.createAgentProjectClientMapper,
                this.agentClientMapper,
                this.atmssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.agentProjectApi, this.createAgentProjectClientMapper, this.agentClientMapper, this.atmssoxClientCallExecutor);
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
}
