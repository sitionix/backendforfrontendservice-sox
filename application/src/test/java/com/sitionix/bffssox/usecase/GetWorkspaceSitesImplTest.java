package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.WorkspaceClient;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
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
class GetWorkspaceSitesImplTest {

    private GetWorkspaceSites getWorkspaceSites;

    @Mock
    private WorkspaceClient workspaceClient;

    @BeforeEach
    void setUp() {
        this.getWorkspaceSites = new GetWorkspaceSitesImpl(this.workspaceClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.workspaceClient);
    }

    @Test
    void givenWorkspaceQueryParams_whenExecute_thenReturnWorkspaceSitesPage() {
        //given
        final WorkspaceSitesPage response = mock(WorkspaceSitesPage.class);
        final Integer page = 0;
        final Integer size = 20;
        when(this.workspaceClient.getWorkspaceSites(page, size)).thenReturn(response);

        //when
        final WorkspaceSitesPage actual = this.getWorkspaceSites.execute(page, size);

        //then
        assertThat(actual).isEqualTo(response);
        verify(this.workspaceClient).getWorkspaceSites(page, size);
    }
}
