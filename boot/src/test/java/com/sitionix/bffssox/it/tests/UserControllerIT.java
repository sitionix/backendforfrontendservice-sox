package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@IntegrationTest
class UserControllerIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("Should register without siteId")
    void givenUserRegisterRequestWithoutSiteId_whenRegisterUser_thenReturnRegisterUserResponse() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_REGISTER_USER)
                .createDefault(d -> d.mutateRequest(r -> r.setSiteId(null)));

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_REGISTER_USER)
                .assertDefault(d -> d.mutateRequest(r -> r.setSiteId(null)));

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return register response when user register request is provided")
    void givenUserRegisterRequest_whenRegisterUser_thenReturnRegisterUserResponse() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_REGISTER_USER)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_REGISTER_USER)
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return unauthorized with body when register request is rejected upstream")
    void givenUnauthorizedRegisterRequest_whenRegisterUser_thenReturnUnauthorizedWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_REGISTER_USER)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingRegisterUserUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_REGISTER_USER)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultRegisterUserUnauthorized.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return internal server error with body when upstream fails")
    void givenUpstreamServerError_whenRegisterUser_thenReturnInternalServerErrorWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_REGISTER_USER)
                .applyDefault(context -> context.responseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .responseBody("responseDefaultMappingRegisterUserServerError.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_REGISTER_USER)
                .applyDefault(context -> context.expectStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .expectResponse("responseDefaultRegisterUserBadGateway.json"))
                .assertDefault();

        requestBuilder.verify();
    }
}
