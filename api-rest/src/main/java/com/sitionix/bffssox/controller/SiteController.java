package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.SiteApi;
import com.app_afesox.bffssox.api_first.dto.CreateSiteRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateSiteResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import com.sitionix.bffssox.mapper.CreateSiteApiMapper;
import com.sitionix.bffssox.usecase.CreateSite;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SiteController implements SiteApi {

    private final CreateSiteApiMapper createSiteApiMapper;

    private final CreateSite createSite;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CreateSiteResponseDTO> createSite(@Valid final CreateSiteRequestDTO createSiteRequestDTO) {
        final CreateSiteRequest request = this.createSiteApiMapper.asCreateSiteRequest(createSiteRequestDTO);
        final CreateSiteResponse response = this.createSite.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.createSiteApiMapper.asCreateSiteResponseDto(response));
    }
}
