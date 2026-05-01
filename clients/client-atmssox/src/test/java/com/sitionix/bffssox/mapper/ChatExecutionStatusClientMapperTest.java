package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.ExecutionStatusDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChatExecutionStatusClientMapperTest {

    private ChatExecutionStatusClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ChatExecutionStatusClientMapperImpl();
    }

    @Test
    void givenAllLifecycleStatesFromAtms_whenMapExecutionStatus_thenReturnCanonicalExternalStates() {
        //given
        final ExecutionStatusDTO accepted = ExecutionStatusDTO.ACCEPTED;
        final ExecutionStatusDTO inProgress = ExecutionStatusDTO.IN_PROGRESS;
        final ExecutionStatusDTO succeeded = ExecutionStatusDTO.SUCCEEDED;
        final ExecutionStatusDTO failed = ExecutionStatusDTO.FAILED;

        //when
        final String acceptedActual = this.mapper.mapExecutionStatus(accepted);
        final String inProgressActual = this.mapper.mapExecutionStatus(inProgress);
        final String succeededActual = this.mapper.mapExecutionStatus(succeeded);
        final String failedActual = this.mapper.mapExecutionStatus(failed);

        //then
        assertThat(acceptedActual).isEqualTo("QUEUED");
        assertThat(inProgressActual).isEqualTo("IN_PROGRESS");
        assertThat(succeededActual).isEqualTo("COMPLETED");
        assertThat(failedActual).isEqualTo("FAILED");
    }

    @Test
    void givenNullStatus_whenMapExecutionStatus_thenReturnNull() {
        //given
        final ExecutionStatusDTO given = null;

        //when
        final String actual = this.mapper.mapExecutionStatus(given);

        //then
        assertThat(actual).isNull();
    }
}
