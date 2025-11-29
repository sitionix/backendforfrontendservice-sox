package com.sitionix.bffssox.it.tests;


import com.sitionix.forgeit.core.test.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


@IntegrationTest
class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CheckForge checkForge;

    @Test
    void givenUserLoginRequest_whenLogin_thenReturnLoginResponse() throws Exception {

        //given
//        final String request = this.resourceManager.request().getFromFile("requestLoginUserWithHappyPath.json");
//
//        final RequestBuilder requestBuilder = this.resourceManager.wireMockJournal()
//                .createMapping()
//                .matchesJson("requestMappingLoginUserWithHappyPath.json")
//                .method(HttpMethod.POST)
//                .urlPath(WireMockEndpoint.LOGIN_USER)
//                .responseStatus(HttpStatus.OK)
//                .responseBody("responseMappingLoginUserWithHappyPath.json")
//                .create();
//
//        final String expected = this.resourceManager.expected().getFromFile("expectedResponseLoginUserWithHappyPath.json");
//
//        //when //then
//        this.mockMvc.perform(post("/api/v1/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonEqualsIgnore(expected))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        requestBuilder.verify();
    }

}
