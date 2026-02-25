package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@IntegrationTest
@TestPropertySource(properties = "api.rest.client.athssox.base-path=http://localhost:1/authsox")
class AuthControllerUpstreamUnavailableIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("Should return service unavailable with body when auth service is unavailable")
    void givenAuthServiceUnavailable_whenLogin_thenReturnServiceUnavailableWithBody() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_LOGIN_USER)
                .applyDefault(context -> context.expectStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .expectResponse("responseDefaultLoginUserBadGatewayWhenUpstreamUnavailable.json"))
                .assertDefault();
    }
}
