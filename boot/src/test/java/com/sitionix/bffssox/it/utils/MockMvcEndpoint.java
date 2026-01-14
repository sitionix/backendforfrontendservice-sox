package com.sitionix.bffssox.it.utils;

import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.bffssox.api_first.dto.RegisterUserDTO;
import com.app_afesox.bffssox.api_first.dto.ResponseRegisterUserDTO;
import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.mockmvc.MockmvcDefault;
import org.springframework.http.HttpStatus;

public class MockMvcEndpoint {

    public static final Endpoint<LoginRequestDTO, LoginResponseDTO> POST_LOGIN_USER =
            Endpoint.createContract("/api/v1/auth/login",
                    HttpMethod.POST,
                    LoginRequestDTO.class,
                    LoginResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultLoginUserWithHappyPath.json")
                            .expectResponse("responseDefaultLoginUserWithHappyPath.json"));

    public static final Endpoint<EmailVerificationDTO, EmailVerificationResponseDTO> POST_VERIFY_EMAIL =
            Endpoint.createContract("/api/v1/auth/email/verify",
                    HttpMethod.POST,
                    EmailVerificationDTO.class,
                    EmailVerificationResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultVerifyEmailWithHappyPath.json")
                            .expectResponse("responseDefaultVerifyEmailWithHappyPath.json"));

    public static final Endpoint<RefreshAccessTokenRequestDTO, RefreshAccessTokenResponseDTO> POST_REFRESH_ACCESS_TOKEN =
            Endpoint.createContract("/api/v1/auth/refresh",
                    HttpMethod.POST,
                    RefreshAccessTokenRequestDTO.class,
                    RefreshAccessTokenResponseDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultRefreshAccessTokenWithHappyPath.json")
                            .expectResponse("responseDefaultRefreshAccessTokenWithHappyPath.json"));

    public static final Endpoint<RegisterUserDTO, ResponseRegisterUserDTO> POST_REGISTER_USER =
            Endpoint.createContract("/api/v1/users",
                    HttpMethod.POST,
                    RegisterUserDTO.class,
                    ResponseRegisterUserDTO.class,
                    (MockmvcDefault) context -> context.expectStatus(HttpStatus.OK.value())
                            .withRequest("requestDefaultRegisterUserWithHappyPath.json")
                            .expectResponse("responseDefaultRegisterUserWithHappyPath.json"));
}
