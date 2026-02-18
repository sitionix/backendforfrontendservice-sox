package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.SiteClient;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSiteImpl implements CreateSite {

    private final SiteClient siteClient;

    @Override
    public CreateSiteResponse execute(final CreateSiteRequest request) {
        return this.siteClient.createSite(request);
    }
}
