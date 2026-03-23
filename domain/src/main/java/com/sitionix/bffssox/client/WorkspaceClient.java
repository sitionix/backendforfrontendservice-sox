package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.SiteOverview;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import java.util.UUID;

/**
 * Client contract for workspace projection operations.
 */
public interface WorkspaceClient {

    /**
     * Loads a paginated list of workspace sites for the authenticated user.
     *
     * @param page page index.
     * @param size page size.
     * @return workspace sites page.
     */
    WorkspaceSitesPage getWorkspaceSites(Integer page, Integer size);

    /**
     * Loads overview data for a single site.
     *
     * @param siteId site identifier.
     * @return site overview data.
     */
    SiteOverview getSiteOverview(UUID siteId);
}
