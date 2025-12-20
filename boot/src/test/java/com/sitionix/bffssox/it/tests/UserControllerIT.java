package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class UserControllerIT {

    @Autowired
    private TestManager testManager;

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
}
