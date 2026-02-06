package com.sitionix.bffssox.it.tests;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sitionix.bffssox.it.utils.MockMvcEndpoint;
import com.sitionix.bffssox.it.utils.WireMockEndpoint;
import com.sitionix.forgeit.core.test.IntegrationTest;
import com.sitionix.forgeit.wiremock.internal.domain.RequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

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
                .applyDefault(context -> context.expectStatus(HttpStatus.UNAUTHORIZED.value())
                        .expectResponse("responseDefaultResendEmailVerificationUnauthorized.json"))
                .assertDefault();
    }

    @Test
    @DisplayName("Should return accepted when resend email verification is called with valid user token")
    void givenValidUserToken_whenResendEmailVerification_thenReturnAccepted() throws Exception {
        //given
        this.testManager.wiremock()
                .createMapping(WireMockEndpoint.GET_JWKS)
                .createDefault();
        final RequestBuilder<?, ?> requestBuilder = this.testManager.wiremock()
                .createMapping(WireMockEndpoint.POST_RESEND_EMAIL_VERIFICATION)
                .createDefault();
        final String privateKeyBase64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDaSTNj6Ka+S9iQ5okHzCYtouf6CnlOxyY16b4+PUE1fSJkjofg6V3OqPUTqze61jKf5ROpYCuW1nfEGqiyi+3win4iqOnNTHyjxqjWBM0MyrAntVy5jVGyzRIgvDN8d/fdZaLbBy5OqO0/eUYan5mbWXTCIOpNUTBU3RoJsJsvMpvDBOiFjmu/ie6slFtYjOMwogt3SReJehZTgTkQNAkauI63Ag9pVlwwRST1Bh5Caihf8Ak0Cyz2ehPE3N/mCxZDVMnlNpu0eCcFj/dF/Arv4CKUwkbNvpd+eyde6/Aw5hMbkhXPWOwCiZ9jthYOQT9aFxd9f8y+7NxvtqQ9W0krAgMBAAECggEAZwJqRPiST3UdYuvXuJ1HWvvoet+cdWeXiZOfmmFvt10w8MEuMB4Qjai/1qNS7lGMnFYPHDvs9gaJiDqIZ5GPHJdOhO6vPpmcq/Dg1fZ4CVRRIrv8YPfmmPkbV+VYaqF2j70ZyECwQqCHLQ/+3Ct64ouphL7hstDgfPvGANKduPxKReVgxD8rDbhqhZ48ADbDrE9Ixj+ApXh5wLqjzA927fryrX4t4koX4gcxCaMTdwcQVPdUPuYAIMxN1TRsXiQLpiDMbigj5skdc11/TMdAhza1IaKnH/7KLAjV1knJhwqCy+Tcl2OO7P62hY6h+u0cNoRvmNy7ppq3DgJ/uopo6QKBgQD91ZaLEHxKvGPe5SawS8SCvVXE3BJdHvtRuOzYaQO2fwiJfCELNOo8P3pna40Fyx9Aa5jwC2SZfD5hoyTD5C/PTr6XRbC2qcYZIlam1tyoNJHH9xMSdeVN1zvPOEWW1eJ2MvfLX4nBJ39quuwcR6Bo7uCkVs4mLTPbvuYSg1T89QKBgQDcJfg/Q44VN7trfFx6lMSz1GCjTWCr45yEAynMtfZH0Z3rlR0iGXRLFQwKnnjlZIGmnpRK4VRHx+f86kx/Fb59VEnCFxIwxhUtEK9Fq8SHVuW5CQNSru3sGhC8BuZztFQhS+cxi0f+By/jnqwp6/vwcDC+4UrMOrS53KXorH1ZnwKBgQClG7p5qHaAknwP71Q0FoSzWf6hN1kGk3F8HeKJff15RrZB36kKxheaqtuuy8mGPDKOz65Cbda485UMI3d1qEbk/N6CPUWN+26syKY+jcIn81HkFTWlq2RFNrxjtMGnNGbC5bJC6lkLd+qsfPu6BWk0+DTNEN20/XAe/tRetGiixQKBgC1pi7cJKXMJBxlaEv2aWKU75x9oo8txbWTx/hpjYsVX4TqqjNLu60VdtxktrYSsYe5MAg0cRDV2cE2Ey3jftGbID1sBRVhYVB/ytCUsFKNScprj2BKjUSMGEXOn/LvbdAtWS/+1Wol1VBShM6SGMHddvUBy2uocJhG9CWBgzI7DAoGBAMeU75b2cdBSq+CZDzoA16Xm+BhOD2aQLiYPsCKz4mZ1dAPQyuy6OD9qYCyEuTdQOrCinDAeE5V4dGCOHbNBF9dGwNeHVm41q5v41dlKDCAfdDKv4x8Sw/zNtxioj31/eijV8nDBPwV/jT2NutKkKfKj1soTed6FzLx0HMeDHKpv";
        final byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory
                .generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        final Instant now = Instant.now();
        final String token = JWT.create()
                .withKeyId("bff-it-kid")
                .withSubject("123")
                .withIssuedAt(Date.from(now.minusSeconds(5)))
                .withExpiresAt(Date.from(now.plusSeconds(300)))
                .sign(Algorithm.RSA256(null, privateKey));

        //when then
        this.testManager.mockMvc()
                .ping(MockMvcEndpoint.POST_RESEND_EMAIL_VERIFICATION)
                .token("Bearer " + token)
                .assertDefault();

        requestBuilder.verify();
    }
}
