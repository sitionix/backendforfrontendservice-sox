package com.sitionix.bffssox.mapper;

import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceSiteClientMapper {

    WorkspaceSitesPage asWorkspaceSitesPage(WorkspaceSitesPageDTO src);
}
