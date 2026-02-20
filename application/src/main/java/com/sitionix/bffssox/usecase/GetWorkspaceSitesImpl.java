package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.WorkspaceClient;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkspaceSitesImpl implements GetWorkspaceSites {

    private final WorkspaceClient workspaceClient;

    @Override
    public WorkspaceSitesPage execute(final Integer page, final Integer size) {
        return this.workspaceClient.getWorkspaceSites(page, size);
    }
}
