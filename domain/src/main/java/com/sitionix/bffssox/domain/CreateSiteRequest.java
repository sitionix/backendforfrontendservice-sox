package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSiteRequest {

    private String name;

    private SiteType type;

    private String description;

    private SiteTemplate template;
}
