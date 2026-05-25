package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.mockmvc.api.PathParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@IntegrationTest(preparations = AuthJwksDataPreparation.class)
@TestPropertySource(properties = "api.rest.client.atmssox.base-path=http://localhost:1/atmssox")
class AgentConversationControllerUpstreamUnavailableIT {

    @Autowired
    private TestManager testManager;

    @Test
    @DisplayName("given automation service unavailable when submit conversation execution then return service unavailable with body")
    void givenAutomationServiceUnavailable_whenSubmitConversationExecution_thenReturnServiceUnavailableWithBody() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_CONVERSATION_EXECUTION)
                .withPathParameters(PathParams.create()
                        .add("conversationId", "11111111-1111-1111-1111-111111111111"))
                .applyDefault(context -> context.expectStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .expectResponse("responseDefaultLoginUserBadGatewayWhenUpstreamUnavailable.json"))
                .assertDefault();
    }
}
