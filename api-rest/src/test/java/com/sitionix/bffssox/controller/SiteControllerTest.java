package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.CreateSiteRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateSiteResponseDTO;
import com.app_afesox.bffssox.api_first.dto.WorkspaceSitesResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import com.sitionix.bffssox.mapper.CreateSiteApiMapper;
import com.sitionix.bffssox.mapper.WorkspaceApiMapper;
import com.sitionix.bffssox.usecase.CreateSite;
import com.sitionix.bffssox.usecase.GetWorkspaceSites;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SiteControllerTest {

    private SiteController siteController;

    @Mock
    private CreateSiteApiMapper createSiteApiMapper;

    @Mock
    private CreateSite createSite;

    @Mock
    private WorkspaceApiMapper workspaceApiMapper;

    @Mock
    private GetWorkspaceSites getWorkspaceSites;

    @BeforeEach
    void setUp() {
        this.siteController = new SiteController(
                this.createSiteApiMapper,
                this.createSite,
                this.workspaceApiMapper,
                this.getWorkspaceSites
        );
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.createSiteApiMapper, this.createSite, this.workspaceApiMapper, this.getWorkspaceSites);
    }

    @Test
    void givenCreateSiteRequestDto_whenCreateSite_thenReturnCreatedResponse() {
        //given
        final CreateSiteRequestDTO createSiteRequestDTO = mock(CreateSiteRequestDTO.class);
        final CreateSiteRequest createSiteRequest = mock(CreateSiteRequest.class);
        final CreateSiteResponse createSiteResponse = mock(CreateSiteResponse.class);
        final CreateSiteResponseDTO createSiteResponseDTO = mock(CreateSiteResponseDTO.class);

        when(this.createSiteApiMapper.asCreateSiteRequest(createSiteRequestDTO)).thenReturn(createSiteRequest);
        when(this.createSite.execute(createSiteRequest)).thenReturn(createSiteResponse);
        when(this.createSiteApiMapper.asCreateSiteResponseDto(createSiteResponse)).thenReturn(createSiteResponseDTO);

        //when
        final ResponseEntity<CreateSiteResponseDTO> actual = this.siteController.createSite(createSiteRequestDTO);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(createSiteResponseDTO));

        verify(this.createSiteApiMapper).asCreateSiteRequest(createSiteRequestDTO);
        verify(this.createSite).execute(createSiteRequest);
        verify(this.createSiteApiMapper).asCreateSiteResponseDto(createSiteResponse);
    }

    @Test
    void givenValidParams_whenGetSites_thenReturnOkResponse() {
        //given
        final Integer page = 0;
        final Integer size = 20;
        final WorkspaceSitesPage response = mock(WorkspaceSitesPage.class);
        final WorkspaceSitesResponseDTO responseDTO = mock(WorkspaceSitesResponseDTO.class);
        when(this.getWorkspaceSites.execute(page, size)).thenReturn(response);
        when(this.workspaceApiMapper.asWorkspaceSitesResponseDto(response)).thenReturn(responseDTO);

        //when
        final ResponseEntity<WorkspaceSitesResponseDTO> actual = this.siteController.getSites(page, size);

        //then
        assertThat(actual).isEqualTo(ResponseEntity.ok(responseDTO));
        verify(this.getWorkspaceSites).execute(page, size);
        verify(this.workspaceApiMapper).asWorkspaceSitesResponseDto(response);
    }
}
