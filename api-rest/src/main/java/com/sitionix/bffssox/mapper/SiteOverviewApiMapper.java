package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.bffssox.domain.SiteOverview;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteOverviewApiMapper {

    SiteOverviewDTO asSiteOverviewDto(SiteOverview src);
}
