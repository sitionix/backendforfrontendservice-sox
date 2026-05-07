package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AgentProjectTest {

    @Test
    void givenSameValues_whenEquals_thenReturnTrue() {
        //given
        final AgentProject given = this.agentProject("Context");
        final AgentProject expected = this.agentProject("Context");

        //when
        final boolean actual = given.equals(expected);

        //then
        assertThat(actual).isTrue();
        assertThat(given).hasSameHashCodeAs(expected);
    }

    @Test
    void givenAgentProject_whenGetContext_thenReturnContext() {
        //given
        final AgentProject given = this.agentProject("Context");

        //when
        final String actual = given.getContext();

        //then
        assertThat(actual).isEqualTo("Context");
    }

    private AgentProject agentProject(final String context) {
        return AgentProject.builder()
                .id(UUID.fromString("9df8ca36-d8c8-4703-9f8c-c8d50b5d4794"))
                .name("Project")
                .description("Description")
                .context(context)
                .status(AgentProjectStatus.ACTIVE)
                .createdAt(OffsetDateTime.parse("2026-04-10T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-04-10T10:01:00Z"))
                .build();
    }
}
