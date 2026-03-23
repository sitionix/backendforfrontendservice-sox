package com.sitionix.bffssox.mapper;

import com.app_afesox.wagssox.client.dto.SiteOverviewDTO;
import com.sitionix.bffssox.domain.SiteOverview;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteOverviewClientMapper {

    SiteOverview asSiteOverview(SiteOverviewDTO src);
}
