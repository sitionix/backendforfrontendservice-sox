package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.infra.IntegrationTest;
import com.sitionix.bffssox.it.infra.utils.TestResourceManager;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.RequestBuilder;
import com.sitionix.bffssox.it.infra.utils.wiremock.domain.check.WireMockEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sitionix.bffssox.it.infra.utils.comparator.CustomResultMatcher.jsonEqualsIgnore;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestResourceManager resourceManager;

    @Autowired
    CheckForge checkForge;

    @Test
    void givenUserLoginRequest_whenLogin_thenReturnLoginResponse() throws Exception {

        //given
        final String request = this.resourceManager.request().getFromFile("requestLoginUserWithHappyPath.json");

        final RequestBuilder requestBuilder = this.resourceManager.wireMockJournal()
                .createMapping()
                .matchesJson("requestMappingLoginUserWithHappyPath.json")
                .method(HttpMethod.POST)
                .urlPath(WireMockEndpoint.LOGIN_USER)
                .responseStatus(HttpStatus.OK)
                .responseBody("responseMappingLoginUserWithHappyPath.json")
                .create();

        final String expected = this.resourceManager.expected().getFromFile("expectedResponseLoginUserWithHappyPath.json");

        //when //then
        this.mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonEqualsIgnore(expected))
                .andExpect(status().isOk())
                .andReturn();

        requestBuilder.verify();
    }

}
