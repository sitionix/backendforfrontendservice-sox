package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.WorkspaceSitesPage;

/**
 * Use case for loading workspace sites page.
 */
public interface GetWorkspaceSites {

    /**
     * Returns a page of workspace sites for the authenticated user.
     *
     * @param page page index.
     * @param size page size.
     * @return workspace sites page.
     */
    WorkspaceSitesPage execute(Integer page, Integer size);
}
