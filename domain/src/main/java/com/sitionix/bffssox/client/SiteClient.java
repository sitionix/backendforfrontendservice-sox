package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;

public interface SiteClient {

    /**
     * Creates a new site for the current authenticated user.
     *
     * @param request creation payload
     * @return created site details
     */
    CreateSiteResponse createSite(CreateSiteRequest request);
}
