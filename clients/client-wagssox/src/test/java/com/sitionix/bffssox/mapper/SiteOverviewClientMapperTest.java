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
