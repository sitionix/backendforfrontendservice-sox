package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.WorkspaceSitesResponseDTO;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceApiMapper {

    WorkspaceSitesResponseDTO asWorkspaceSitesResponseDto(WorkspaceSitesPage src);
}
