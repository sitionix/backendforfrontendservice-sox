package com.sitionix.bffssox.client;

import com.app_afesox.athssox.client.api.AuthApi;
import com.app_afesox.athssox.client.dto.EmailVerificationDTO;
import com.app_afesox.athssox.client.dto.EmailVerificationResponseDTO;
import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenResponseDTO;
import com.app_afesox.athssox.client.invoker.ApiClient;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import com.sitionix.bffssox.mapper.EmailVerificationClientMapper;
import com.sitionix.bffssox.mapper.LoginUserClientMapper;
import com.sitionix.bffssox.mapper.RefreshAccessTokenClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserClientImpl implements AuthUserClient {

    private final AuthApi authApi;

    private final LoginUserClientMapper clientMapper;

    private final EmailVerificationClientMapper emailVerificationClientMapper;

    private final RefreshAccessTokenClientMapper refreshAccessTokenClientMapper;

    private final ClientCallExecutor clientCallExecutor;

    @Override
    public LoginResponse login(final LoginRequest request) {
        final LoginRequestDTO requestDTO = this.clientMapper.asLoginRequestDto(request);
        final LoginResponseDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.authApi.login(requestDTO)
        );
        return this.clientMapper.asLoginResponse(responseDTO);
    }

    @Override
    public EmailVerificationResponse verifyEmail(final EmailVerificationRequest request) {
        final EmailVerificationDTO requestDTO = this.emailVerificationClientMapper.asEmailVerificationDto(request);
        final EmailVerificationResponseDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.authApi.verifyEmail(requestDTO)
        );
        return this.emailVerificationClientMapper.asEmailVerificationResponse(responseDTO);
    }

    @Override
    public RefreshAccessTokenResponse refreshAccessToken(final RefreshAccessTokenRequest request) {
        final RefreshAccessTokenRequestDTO requestDTO =
                this.refreshAccessTokenClientMapper.asRefreshAccessTokenRequestDto(request);
        final RefreshAccessTokenResponseDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.invokeRefreshAccessToken(requestDTO)
        );
        return this.refreshAccessTokenClientMapper.asRefreshAccessTokenResponse(responseDTO);
    }

    private RefreshAccessTokenResponseDTO invokeRefreshAccessToken(final RefreshAccessTokenRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Missing the required parameter 'refreshAccessTokenRequestDTO' when calling refreshAccessToken");
        }

        final ApiClient apiClient = this.authApi.getApiClient();
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final List<MediaType> accept = apiClient.selectHeaderAccept(new String[]{"application/json"});
        final MediaType contentType = apiClient.selectHeaderContentType(new String[]{"application/json"});
        final String[] authNames = new String[]{};
        final ParameterizedTypeReference<RefreshAccessTokenResponseDTO> returnType =
                new ParameterizedTypeReference<>() {
                };

        return apiClient.invokeAPI("/api/v1/auth/refresh",
                HttpMethod.POST,
                Collections.emptyMap(),
                queryParams,
                requestDTO,
                headerParams,
                cookieParams,
                formParams,
                accept,
                contentType,
                authNames,
                returnType).getBody();
    }
}
