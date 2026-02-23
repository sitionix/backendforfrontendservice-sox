package com.sitionix.bffssox.it.tests;

import com.sitionix.bffssox.it.preparation.AuthJwksDataPreparation;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

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
    @DisplayName("Should return unauthorized with body when invalid verify email token is provided")
    void givenInvalidVerifyEmailToken_whenVerifyEmail_thenReturnUnauthorizedWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_VERIFY_EMAIL)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingVerifyEmailUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_VERIFY_EMAIL)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
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
                .assertDefault();

        requestBuilder.verify();
    }

    @Test
    @DisplayName("Should return unauthorized with body when invalid refresh token is provided")
    void givenInvalidRefreshAccessToken_whenRefreshAccessToken_thenReturnUnauthorizedWithBody() {
        //given
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_REFRESH_ACCESS_TOKEN)
                .applyDefault(context -> context.responseStatus(HttpStatus.UNAUTHORIZED.value())
                        .responseBody("responseDefaultMappingRefreshAccessTokenUnauthorized.json"))
                .createDefault();

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_REFRESH_ACCESS_TOKEN)
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
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
