package com.sitionix.bffssox.it.utils;

import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.sitionix.forgeit.domain.endpoint.Endpoint;
import com.sitionix.forgeit.domain.endpoint.HttpMethod;
import com.sitionix.forgeit.domain.endpoint.mockmvc.MockmvcDefault;
import org.springframework.http.HttpStatus;

public class MockMvcEndpoint {

    public static Endpoint<LoginRequestDTO, LoginResponseDTO> POST_LOGIN_USER =
            Endpoint.createContract("/api/v1/auth/login",
                    HttpMethod.POST,
                    LoginRequestDTO.class,
                    LoginResponseDTO.class,
                    (MockmvcDefault) context -> context.status(HttpStatus.OK.value())
                            .request("requestDefaultLoginUserWithHappyPath.json")
                            .response("responseDefaultLoginUserWithHappyPath.json"));
}
