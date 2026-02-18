package com.sitionix.bffssox.client;

import com.app_afesox.stsssox.client.api.SiteApi;
import com.app_afesox.stsssox.client.dto.CreateSiteRequestDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import com.sitionix.bffssox.mapper.CreateSiteClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteClientImpl implements SiteClient {

    private final SiteApi siteApi;

    private final CreateSiteClientMapper createSiteClientMapper;

    private final StsssoxClientCallExecutor clientCallExecutor;

    @Override
    public CreateSiteResponse createSite(final CreateSiteRequest request) {
        final CreateSiteRequestDTO requestDTO = this.createSiteClientMapper.asCreateSiteRequestDto(request);
        final CreateSiteResponseDTO responseDTO = this.clientCallExecutor.execute(
                () -> this.siteApi.createSite(requestDTO)
        );
        return this.createSiteClientMapper.asCreateSiteResponse(responseDTO);
    }
}
