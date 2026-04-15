package com.sitionix.bffssox.mapper;

import com.app_afesox.wagssox.client.dto.SiteOverviewDTO;
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
class SiteOverviewClientMapperTest {

    private SiteOverviewClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new SiteOverviewClientMapperImpl();
    }

    @Test
    void givenSiteOverviewDto_whenAsSiteOverview_thenReturnSiteOverview() {
        //given
        final SiteOverviewDTO responseDTO = this.getSiteOverviewDto();
        final SiteOverview expected = this.getSiteOverview();

        //when
        final SiteOverview actual = this.mapper.asSiteOverview(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenSiteOverviewDtoWithArchivedStatusAndOtherType_whenAsSiteOverview_thenReturnMappedEnums() {
        //given
        final SiteOverviewDTO responseDTO = new SiteOverviewDTO()
                .siteId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("n")
                .status(SiteOverviewDTO.StatusEnum.ARCHIVED)
                .type(SiteOverviewDTO.TypeEnum.OTHER)
                .description("d")
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"));

        //when
        final SiteOverview actual = this.mapper.asSiteOverview(responseDTO);

        //then
        assertThat(actual.getStatus()).isEqualTo(SiteStatus.ARCHIVED);
        assertThat(actual.getType()).isEqualTo(SiteType.OTHER);
    }

    @Test
    void givenNullSiteOverviewDto_whenAsSiteOverview_thenReturnNull() {
        //given
        final SiteOverviewDTO responseDTO = null;

        //when
        final SiteOverview actual = this.mapper.asSiteOverview(responseDTO);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenAllEnums_whenAsSiteOverview_thenReturnMappedEnums() {
        //given
        final UUID siteId = UUID.fromString("00000000-0000-0000-0000-000000000004");
        final OffsetDateTime createdAt = OffsetDateTime.parse("2026-01-10T12:00:00Z");
        final OffsetDateTime updatedAt = OffsetDateTime.parse("2026-01-29T08:30:00Z");

        //when
        for (final SiteOverviewDTO.StatusEnum statusEnum : SiteOverviewDTO.StatusEnum.values()) {
            final SiteOverviewDTO siteOverviewDTO = new SiteOverviewDTO()
                    .siteId(siteId)
                    .name("n")
                    .status(statusEnum)
                    .type(SiteOverviewDTO.TypeEnum.PORTFOLIO)
                    .description("d")
                    .createdAt(createdAt)
                    .updatedAt(updatedAt);
            final SiteOverview actual = this.mapper.asSiteOverview(siteOverviewDTO);
            assertThat(actual.getStatus().name()).isEqualTo(statusEnum.name());
        }
        for (final SiteOverviewDTO.TypeEnum typeEnum : SiteOverviewDTO.TypeEnum.values()) {
            final SiteOverviewDTO siteOverviewDTO = new SiteOverviewDTO()
                    .siteId(siteId)
                    .name("n")
                    .status(SiteOverviewDTO.StatusEnum.DRAFT)
                    .type(typeEnum)
                    .description("d")
                    .createdAt(createdAt)
                    .updatedAt(updatedAt);
            final SiteOverview actual = this.mapper.asSiteOverview(siteOverviewDTO);
            assertThat(actual.getType().name()).isEqualTo(typeEnum.name());
        }

        //then
        assertThat(SiteOverviewDTO.TypeEnum.values().length).isGreaterThan(0);
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
}
