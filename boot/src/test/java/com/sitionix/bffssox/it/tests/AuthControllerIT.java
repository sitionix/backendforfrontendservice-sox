package com.sitionix.bffssox.it.tests;


import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@IntegrationTest
class AuthControllerIT {
    @Autowired
    private TestManager testManager;

    @Test
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
    void givenInvalidCredentials_whenLogin_thenReturnUnauthorizedWithBody() {

        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_LOGIN_USER_UNAUTHORIZED)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_LOGIN_USER_UNAUTHORIZED)
                .assertDefault();

        requestBuilder.verify();
    }

}
