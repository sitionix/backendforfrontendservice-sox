package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.mockmvc.api.PathParams;
import com.sitionix.forgeit.wiremock.api.WireMockPathParams;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@IntegrationTest(preparations = AuthJwksDataPreparation.class)
class AgentLifecycleControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given valid user token when activate agent then return active agent")
    void givenValidUserToken_whenActivateAgent_thenReturnActiveAgent() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_ACTIVATE_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ACTIVATE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given valid user token when archive agent then return archived agent")
    void givenValidUserToken_whenArchiveAgent_thenReturnArchivedAgent() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_ARCHIVE_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ARCHIVE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given invalid agent id when activate agent then return bad request")
    void givenInvalidAgentId_whenActivateAgent_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ACTIVATE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultActivateAgentInvalidAgentId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when activate agent then return unauthorized and do not call upstream")
    void givenMissingToken_whenActivateAgent_thenReturnUnauthorizedAndDoNotCallUpstream() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ACTIVATE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given missing token when archive agent then return unauthorized")
    void givenMissingToken_whenArchiveAgent_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ARCHIVE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultGetSitesUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given invalid agent id when archive agent then return bad request")
    void givenInvalidAgentId_whenArchiveAgent_thenReturnBadRequest() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ARCHIVE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "not-a-valid-id"))
                .applyDefault(context -> context.expectStatus(HttpStatus.BAD_REQUEST.value())
                        .expectResponse("responseDefaultActivateAgentInvalidAgentId.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("given invalid transition conflict from upstream when activate agent then return conflict with body")
    void givenInvalidTransitionConflictFromUpstream_whenActivateAgent_thenReturnConflictWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_ACTIVATE_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.responseStatus(HttpStatus.CONFLICT.value())
                        .responseBody("responseDefaultMappingActivateAgentInvalidTransitionConflict.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ACTIVATE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectStatus(HttpStatus.CONFLICT.value())
                        .expectResponse("responseDefaultActivateAgentInvalidTransitionConflict.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given invalid transition conflict from upstream when archive agent then return conflict with body")
    void givenInvalidTransitionConflictFromUpstream_whenArchiveAgent_thenReturnConflictWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_ARCHIVE_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.responseStatus(HttpStatus.CONFLICT.value())
                        .responseBody("responseDefaultMappingArchiveAgentInvalidTransitionConflict.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ARCHIVE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectStatus(HttpStatus.CONFLICT.value())
                        .expectResponse("responseDefaultArchiveAgentInvalidTransitionConflict.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("given upstream not found when archive agent then return not found with body")
    void givenUpstreamNotFound_whenArchiveAgent_thenReturnNotFoundWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_ARCHIVE_AGENT)
                .pathPattern(WireMockPathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.responseStatus(HttpStatus.NOT_FOUND.value())
                        .responseBody("responseDefaultMappingArchiveAgentNotFound.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_ARCHIVE_AGENT)
                .withPathParameters(PathParams.create()
                        .add("agentId", "4e0c95eb-9e63-4b3f-98f4-2c8c713233c0"))
                .applyDefault(context -> context.expectStatus(HttpStatus.NOT_FOUND.value())
                        .expectResponse("responseDefaultArchiveAgentNotFound.json"))
                .assertDefault();

        requestBuilder.verify();
    }
}
