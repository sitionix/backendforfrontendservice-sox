package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.dto.CreateSiteRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateSiteResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import com.sitionix.bffssox.mapper.CreateSiteApiMapper;
import com.sitionix.bffssox.usecase.CreateSite;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SiteControllerTest {

    private SiteController siteController;

    @Mock
    private CreateSiteApiMapper createSiteApiMapper;

    @Mock
    private CreateSite createSite;

    @BeforeEach
    void setUp() {
        this.siteController = new SiteController(this.createSiteApiMapper, this.createSite);
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
}
