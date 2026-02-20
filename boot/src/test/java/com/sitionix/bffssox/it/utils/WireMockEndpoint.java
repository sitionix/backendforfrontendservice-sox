package com.sitionix.bffssox.it.utils;

import com.app_afesox.athssox.client.dto.EmailVerificationDTO;
import com.app_afesox.athssox.client.dto.EmailVerificationResponseDTO;
import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.athssox.client.dto.ResendEmailVerificationResponseDTO;
import com.app_afesox.athssox.client.dto.RegisterUserDTO;
import com.app_afesox.athssox.client.dto.ResponseRegisterUserDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteRequestDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteResponseDTO;
import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.wiremock.WiremockDefault;
import com.sitionix.forgeit.wiremock.api.Parameter;

public class WireMockEndpoint {

    public static final Endpoint<LoginRequestDTO, LoginResponseDTO> POST_LOGIN_USER =
            Endpoint.createContract("/authsox/api/v1/auth/login",
                    HttpMethod.POST,
                    LoginRequestDTO.class,
                    LoginResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingLoginUserWithHappyPath.json")
                                .responseBody("responseDefaultMappingLoginUserWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<EmailVerificationDTO, EmailVerificationResponseDTO> POST_VERIFY_EMAIL =
            Endpoint.createContract("/authsox/api/v1/auth/email/verify",
                    HttpMethod.POST,
                    EmailVerificationDTO.class,
                    EmailVerificationResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingVerifyEmailWithHappyPath.json")
                                .responseBody("responseDefaultMappingVerifyEmailWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<RefreshAccessTokenRequestDTO, RefreshAccessTokenResponseDTO> POST_REFRESH_ACCESS_TOKEN =
            Endpoint.createContract("/authsox/api/v1/auth/refresh",
                    HttpMethod.POST,
                    RefreshAccessTokenRequestDTO.class,
                    RefreshAccessTokenResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingRefreshAccessTokenWithHappyPath.json")
                                .responseBody("responseDefaultMappingRefreshAccessTokenWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<Object, ResendEmailVerificationResponseDTO> POST_RESEND_EMAIL_VERIFICATION =
            Endpoint.createContract("/authsox/api/v1/auth/email/verify/resend",
                    HttpMethod.POST,
                    Object.class,
                    ResendEmailVerificationResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingResendEmailVerificationWithHappyPath.json")
                                .header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingResendEmailVerificationWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(202);
                    });

    public static final Endpoint<Object, Object> GET_JWKS =
            Endpoint.createContract("/authsox/.well-known/jwks.json",
                    HttpMethod.GET,
                    Object.class,
                    Object.class,
                    (WiremockDefault) context -> {
                        context.responseBody("responseDefaultMappingJwks.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<RegisterUserDTO, ResponseRegisterUserDTO> POST_REGISTER_USER =
            Endpoint.createContract("/authsox/api/v1/users",
                    HttpMethod.POST,
                    RegisterUserDTO.class,
                    ResponseRegisterUserDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingRegisterUserWithHappyPath.json")
                                .responseBody("responseDefaultMappingRegisterUserWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<CreateSiteRequestDTO, CreateSiteResponseDTO> POST_CREATE_SITE =
            Endpoint.createContract("/stsssox/api/v1/sites",
                    HttpMethod.POST,
                    CreateSiteRequestDTO.class,
                    CreateSiteResponseDTO.class,
                    (WiremockDefault) context -> {
                        context.matchesJson("requestDefaultMappingCreateSiteWithHappyPath.json")
                                .header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingCreateSiteWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(201);
                    });

    public static final Endpoint<Void, WorkspaceSitesPageDTO> GET_SITES_FIRST_PAGE =
            Endpoint.createContract("/wagssox/api/v1/sites?page=0&size=20",
                    HttpMethod.GET,
                    Void.class,
                    WorkspaceSitesPageDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingGetSitesFirstPageWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, WorkspaceSitesPageDTO> GET_SITES_NEXT_PAGE =
            Endpoint.createContract("/wagssox/api/v1/sites?page=1&size=20",
                    HttpMethod.GET,
                    Void.class,
                    WorkspaceSitesPageDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingGetSitesNextPageWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });

    public static final Endpoint<Void, WorkspaceSitesPageDTO> GET_SITES_END_OF_LIST =
            Endpoint.createContract("/wagssox/api/v1/sites?page=1&size=20",
                    HttpMethod.GET,
                    Void.class,
                    WorkspaceSitesPageDTO.class,
                    (WiremockDefault) context -> {
                        context.header("X-Forge-User-Sub", Parameter.equalTo("it-user-123"))
                                .header("Authorization", Parameter.matches("Bearer\\s+.+"))
                                .responseBody("responseDefaultMappingGetSitesEndOfListWithHappyPath.json")
                                .plainUrl()
                                .responseStatus(200);
                    });
}
