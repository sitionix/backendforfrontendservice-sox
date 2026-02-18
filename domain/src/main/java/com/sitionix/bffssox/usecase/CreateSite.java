package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;

public interface CreateSite {

    /**
     * Creates a site through the outbound site service client.
     *
     * @param request creation payload
     * @return created site details
     */
    CreateSiteResponse execute(CreateSiteRequest request);
}
