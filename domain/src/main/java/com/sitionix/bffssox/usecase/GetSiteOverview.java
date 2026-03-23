package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.SiteOverview;
import java.util.UUID;

/**
 * Use case for loading overview data for a single site.
 */
public interface GetSiteOverview {

    /**
     * Returns overview data for a single site accessible to the authenticated user.
     *
     * @param siteId site identifier.
     * @return site overview data.
     */
    SiteOverview execute(UUID siteId);
}
