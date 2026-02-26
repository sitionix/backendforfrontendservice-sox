package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;

@IntegrationTest(preparations = AuthJwksDataPreparation.class)
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
                .andExpectPath(MockMvcResultMatchers.header().string(HttpHeaders.SET_COOKIE,
                        containsString("__Host-refresh_token=dGhpc0lzUmVmcmVzaA==")))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return service unavailable with body when invalid credentials are provided upstream")
    void givenInvalidCredentials_whenLogin_thenReturnServiceUnavailableWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_LOGIN_USER)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingLoginUserUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_LOGIN_USER)
                .applyDefault(context -> context.expectStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .expectResponse("responseDefaultLoginUserUnauthorized.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return email verification response when verify email request is provided")
    void givenVerifyEmailRequest_whenVerifyEmail_thenReturnEmailVerificationResponse() {

        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_VERIFY_EMAIL)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_VERIFY_EMAIL)
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return service unavailable with body when invalid verify email token is provided upstream")
    void givenInvalidVerifyEmailToken_whenVerifyEmail_thenReturnServiceUnavailableWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_VERIFY_EMAIL)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingVerifyEmailUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_VERIFY_EMAIL)
                .applyDefault(context -> context.expectStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .expectResponse("responseDefaultVerifyEmailUnauthorized.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return refresh access token response when refresh request is provided")
    void givenRefreshAccessTokenRequest_whenRefreshAccessToken_thenReturnRefreshAccessTokenResponse() {

        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_REFRESH_ACCESS_TOKEN)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_REFRESH_ACCESS_TOKEN)
                .andExpectPath(MockMvcResultMatchers.header().string(HttpHeaders.SET_COOKIE,
                        containsString("__Host-refresh_token=bmV4dFJlZnJlc2g=")))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return service unavailable with body when invalid refresh token is provided upstream")
    void givenInvalidRefreshAccessToken_whenRefreshAccessToken_thenReturnServiceUnavailableWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_REFRESH_ACCESS_TOKEN)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingRefreshAccessTokenUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_REFRESH_ACCESS_TOKEN)
                .applyDefault(context -> context.expectStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .expectResponse("responseDefaultRefreshAccessTokenUnauthorized.json"))
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return unauthorized when resend email verification is called without user token")
    void givenMissingUserToken_whenResendEmailVerification_thenReturnUnauthorized() {
        //given

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_RESEND_EMAIL_VERIFICATION)
                .token(null)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultResendEmailVerificationUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("Should return accepted when resend email verification is called with valid user token")
    void givenValidUserToken_whenResendEmailVerification_thenReturnAccepted() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_RESEND_EMAIL_VERIFICATION)
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_RESEND_EMAIL_VERIFICATION)
                .assertDefault();

        requestBuilder.verify();
    }
}
