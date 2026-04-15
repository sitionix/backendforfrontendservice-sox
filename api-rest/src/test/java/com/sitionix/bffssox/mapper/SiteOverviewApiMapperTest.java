package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.SiteOverviewDTO;
import com.sitionix.bffssox.domain.SiteOverview;
import com.sitionix.bffssox.domain.SiteStatus;
import com.sitionix.bffssox.domain.SiteType;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SiteOverviewApiMapperTest {

    private SiteOverviewApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new SiteOverviewApiMapperImpl();
    }

    @Test
    void givenSiteOverview_whenAsSiteOverviewDto_thenReturnSiteOverviewDto() {
        //given
        final SiteOverview siteOverview = this.getSiteOverview();
        final SiteOverviewDTO expected = this.getSiteOverviewDto();

        //when
        final SiteOverviewDTO actual = this.mapper.asSiteOverviewDto(siteOverview);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullSiteOverview_whenAsSiteOverviewDto_thenReturnNull() {
        //given
        final SiteOverview siteOverview = null;

        //when
        final SiteOverviewDTO actual = this.mapper.asSiteOverviewDto(siteOverview);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenSiteOverviewWithArchivedStatusAndOtherType_whenAsSiteOverviewDto_thenReturnMappedEnums() {
        //given
        final SiteOverview siteOverview = SiteOverview.builder()
                .siteId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("Archived Site")
                .status(SiteStatus.ARCHIVED)
                .type(SiteType.OTHER)
                .description("d")
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                .build();

        //when
        final SiteOverviewDTO actual = this.mapper.asSiteOverviewDto(siteOverview);

        //then
        assertThat(actual.getStatus()).isEqualTo(SiteOverviewDTO.StatusEnum.ARCHIVED);
        assertThat(actual.getType()).isEqualTo(SiteOverviewDTO.TypeEnum.OTHER);
    }

    @Test
    void givenAllEnums_whenAsSiteOverviewDto_thenReturnMappedEnums() {
        //given
        final UUID siteId = UUID.fromString("00000000-0000-0000-0000-000000000003");
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-01-10T12:00:00Z");
        final OffsetDateTime updatedAt = OffsetDateTime.parse("2026-01-29T08:30:00Z");

        //when
        for (final SiteStatus status : SiteStatus.values()) {
            final SiteOverview siteOverview = SiteOverview.builder()
                    .siteId(siteId)
                    .name("n")
                    .status(status)
                    .type(SiteType.PORTFOLIO)
                    .description("d")
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
            final SiteOverviewDTO actual = this.mapper.asSiteOverviewDto(siteOverview);
            assertThat(actual.getStatus().name()).isEqualTo(status.name());
        }
        for (final SiteType type : SiteType.values()) {
            final SiteOverview siteOverview = SiteOverview.builder()
                    .siteId(siteId)
                    .name("n")
                    .status(SiteStatus.DRAFT)
                    .type(type)
                    .description("d")
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
            final SiteOverviewDTO actual = this.mapper.asSiteOverviewDto(siteOverview);
            assertThat(actual.getType().name()).isEqualTo(type.name());
        }

        //then
        assertThat(SiteType.values().length).isGreaterThan(0);
    }

    private SiteOverview getSiteOverview() {
        return SiteOverview.builder()
                .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .name("Agency Portfolio")
                .status(SiteStatus.DRAFT)
                .type(SiteType.PORTFOLIO)
                .description(null)
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                .build();
    }

    private SiteOverviewDTO getSiteOverviewDto() {
        return new SiteOverviewDTO()
                .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .name("Agency Portfolio")
                .status(SiteOverviewDTO.StatusEnum.DRAFT)
                .type(SiteOverviewDTO.TypeEnum.PORTFOLIO)
                .description(null)
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"));
    }
}
