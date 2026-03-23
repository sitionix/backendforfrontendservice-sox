package com.sitionix.bffssox.client;

import com.app_afesox.wagssox.client.api.SiteApi;
import com.app_afesox.wagssox.client.dto.SiteOverviewDTO;
import com.sitionix.bffssox.domain.SiteOverview;
import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import com.sitionix.bffssox.mapper.SiteOverviewClientMapper;
import com.sitionix.bffssox.mapper.WorkspaceSiteClientMapper;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkspaceClientImplTest {

    private WorkspaceClient workspaceClient;

    @Mock
    private SiteApi siteApi;

    @Mock
    private WorkspaceSiteClientMapper workspaceSiteClientMapper;

    @Mock
    private SiteOverviewClientMapper siteOverviewClientMapper;

    @Mock
    private WagssoxClientCallExecutor wagssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.workspaceClient = new WorkspaceClientImpl(
                this.siteApi,
                this.workspaceSiteClientMapper,
                this.siteOverviewClientMapper,
                this.wagssoxClientCallExecutor
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                this.siteApi,
                this.workspaceSiteClientMapper,
                this.siteOverviewClientMapper,
                this.wagssoxClientCallExecutor
        );
    }

    @Test
    void givenWorkspaceParams_whenGetWorkspaceSites_thenReturnWorkspaceSitesPage() {
        //given
        final Integer page = 0;
        final Integer size = 20;
        final WorkspaceSitesPageDTO responseDTO = org.mockito.Mockito.mock(WorkspaceSitesPageDTO.class);
        final WorkspaceSitesPage response = org.mockito.Mockito.mock(WorkspaceSitesPage.class);

        when(this.workspaceSiteClientMapper.asWorkspaceSitesPage(responseDTO)).thenReturn(response);
        when(this.wagssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<WorkspaceSitesPageDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.siteApi.getSites(page, size)).thenReturn(responseDTO);

        //when
        final WorkspaceSitesPage actual = this.workspaceClient.getWorkspaceSites(page, size);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.wagssoxClientCallExecutor).execute(any());
        verify(this.siteApi).getSites(page, size);
        verify(this.workspaceSiteClientMapper).asWorkspaceSitesPage(responseDTO);
    }

    @Test
    void givenSiteId_whenGetSiteOverview_thenReturnSiteOverview() {
        //given
        final UUID siteId = UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003");
        final SiteOverviewDTO responseDTO = org.mockito.Mockito.mock(SiteOverviewDTO.class);
        final SiteOverview response = org.mockito.Mockito.mock(SiteOverview.class);

        when(this.siteOverviewClientMapper.asSiteOverview(responseDTO)).thenReturn(response);
        when(this.wagssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<SiteOverviewDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.siteApi.getSiteOverview(siteId)).thenReturn(responseDTO);

        //when
        final SiteOverview actual = this.workspaceClient.getSiteOverview(siteId);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.wagssoxClientCallExecutor).execute(any());
        verify(this.siteApi).getSiteOverview(siteId);
        verify(this.siteOverviewClientMapper).asSiteOverview(responseDTO);
    }
}
