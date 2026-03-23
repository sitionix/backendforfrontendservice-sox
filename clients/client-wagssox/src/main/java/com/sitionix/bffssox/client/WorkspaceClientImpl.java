package com.sitionix.bffssox.client;

import com.app_afesox.wagssox.client.api.SiteApi;
import com.app_afesox.wagssox.client.dto.SiteOverviewDTO;
import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.bffssox.domain.SiteOverview;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import com.sitionix.bffssox.mapper.SiteOverviewClientMapper;
import com.sitionix.bffssox.mapper.WorkspaceSiteClientMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceClientImpl implements WorkspaceClient {

    private final SiteApi workspaceApi;

    private final WorkspaceSiteClientMapper workspaceSiteClientMapper;
    private final SiteOverviewClientMapper siteOverviewClientMapper;

    private final WagssoxClientCallExecutor wagssoxClientCallExecutor;

    @Override
    public WorkspaceSitesPage getWorkspaceSites(final Integer page, final Integer size) {
        final WorkspaceSitesPageDTO responseDTO = this.wagssoxClientCallExecutor.execute(
                () -> this.workspaceApi.getSites(page, size)
        );
        return this.workspaceSiteClientMapper.asWorkspaceSitesPage(responseDTO);
    }

    @Override
    public SiteOverview getSiteOverview(final UUID siteId) {
        final SiteOverviewDTO responseDTO = this.wagssoxClientCallExecutor.execute(
                () -> this.workspaceApi.getSiteOverview(siteId)
        );
        return this.siteOverviewClientMapper.asSiteOverview(responseDTO);
    }
}
