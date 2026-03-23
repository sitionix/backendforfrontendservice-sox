package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.WorkspaceClient;
import com.sitionix.bffssox.domain.SiteOverview;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSiteOverviewImplTest {

    @Mock
    private WorkspaceClient workspaceClient;

    private GetSiteOverviewImpl getSiteOverview;

    @BeforeEach
    void setUp() {
        this.getSiteOverview = new GetSiteOverviewImpl(this.workspaceClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceClient);
    }

    @Test
    void givenSiteId_whenExecute_thenReturnSiteOverview() {
        //given
        final UUID siteId = UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003");
        final SiteOverview expected = mock(SiteOverview.class);
        when(this.workspaceClient.getSiteOverview(siteId)).thenReturn(expected);

        //when
        final SiteOverview actual = this.getSiteOverview.execute(siteId);

        //then
        assertThat(actual).isEqualTo(expected);
        verify(this.workspaceClient).getSiteOverview(siteId);
    }
}
