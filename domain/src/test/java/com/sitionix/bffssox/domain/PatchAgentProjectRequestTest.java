package com.sitionix.bffssox.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatchAgentProjectRequestTest {

    @Test
    void givenSameValues_whenEquals_thenReturnTrue() {
        //given
        final PatchAgentProjectRequest given = this.patchAgentProjectRequest("Context");
        final PatchAgentProjectRequest expected = this.patchAgentProjectRequest("Context");

        //when
        final boolean actual = given.equals(expected);

        //then
        assertThat(actual).isTrue();
        assertThat(given).hasSameHashCodeAs(expected);
    }

    @Test
    void givenPatchRequest_whenGetContext_thenReturnContext() {
        //given
        final PatchAgentProjectRequest given = this.patchAgentProjectRequest("Context");

        //when
        final String actual = given.getContext();

        //then
        assertThat(actual).isEqualTo("Context");
    }

    private PatchAgentProjectRequest patchAgentProjectRequest(final String context) {
        return PatchAgentProjectRequest.builder()
                .name("Project")
                .description("Description")
                .context(context)
                .build();
    }
}
