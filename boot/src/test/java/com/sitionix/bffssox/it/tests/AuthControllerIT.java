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
class AuthControllerIT {
    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("Should return login response when user login request is provided")
    void givenUserLoginRequest_whenLogin_thenReturnLoginResponse() {

        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_LOGIN_USER)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_LOGIN_USER)
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return unauthorized with body when invalid credentials are provided")
    void givenInvalidCredentials_whenLogin_thenReturnUnauthorizedWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_LOGIN_USER)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingLoginUserUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_LOGIN_USER)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultLoginUserUnauthorized.json"))
                .assertDefault();

        requestBuilder.verify();
    }

}
