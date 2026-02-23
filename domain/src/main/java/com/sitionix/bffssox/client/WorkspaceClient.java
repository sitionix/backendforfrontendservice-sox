package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.WorkspaceSitesPage;

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
}
