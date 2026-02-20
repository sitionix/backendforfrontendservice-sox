package com.sitionix.bffssox.client;

import com.app_afesox.wagssox.client.api.SiteApi;
import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import com.sitionix.bffssox.mapper.WorkspaceSiteClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceClientImpl implements WorkspaceClient {

    private final SiteApi workspaceApi;

    private final WorkspaceSiteClientMapper workspaceSiteClientMapper;

    private final WagssoxClientCallExecutor wagssoxClientCallExecutor;

    @Override
    public WorkspaceSitesPage getWorkspaceSites(final Integer page, final Integer size) {
        final WorkspaceSitesPageDTO responseDTO = this.wagssoxClientCallExecutor.execute(
                () -> this.workspaceApi.getSites(page, size)
        );
        return this.workspaceSiteClientMapper.asWorkspaceSitesPage(responseDTO);
    }
}
