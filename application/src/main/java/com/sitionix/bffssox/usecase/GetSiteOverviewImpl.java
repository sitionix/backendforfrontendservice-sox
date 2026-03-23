package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.WorkspaceClient;
import com.sitionix.bffssox.domain.SiteOverview;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSiteOverviewImpl implements GetSiteOverview {

    private final WorkspaceClient workspaceClient;

    @Override
    public SiteOverview execute(final UUID siteId) {
        return this.workspaceClient.getSiteOverview(siteId);
    }
}
