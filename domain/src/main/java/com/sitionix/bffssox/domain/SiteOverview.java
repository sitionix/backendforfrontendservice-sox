package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteOverview {

    private UUID siteId;

    private String name;

    private SiteStatus status;

    private SiteType type;

    private String description;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
