package com.sitionix.bffssox.it.utils;

import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.wiremock.WiremockDefault;

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
}
