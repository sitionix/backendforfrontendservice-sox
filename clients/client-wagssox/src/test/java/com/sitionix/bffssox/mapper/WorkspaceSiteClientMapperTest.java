package com.sitionix.bffssox.mapper;

import com.app_afesox.wagssox.client.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.bffssox.domain.SiteStatus;
import com.sitionix.bffssox.domain.SiteType;
import com.sitionix.bffssox.domain.WorkspaceSiteCard;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceSiteClientMapperTest {

    private WorkspaceSiteClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new WorkspaceSiteClientMapperImpl();
    }

    @Test
    void givenWorkspaceSitesPageDto_whenAsWorkspaceSitesPage_thenReturnWorkspaceSitesPage() {
        //given
        final WorkspaceSitesPageDTO responseDTO = this.getWorkspaceSitesPageDto();
        final WorkspaceSitesPage expected = this.getWorkspaceSitesPage();

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenWorkspaceSitesPageDtoWithAllStatusAndTypeVariants_whenAsWorkspaceSitesPage_thenReturnMappedWorkspaceSitesPage() {
        //given
        final WorkspaceSitesPageDTO responseDTO = this.getWorkspaceSitesPageDtoWithAllStatusAndTypeVariants();
        final WorkspaceSitesPage expected = this.getWorkspaceSitesPageWithAllStatusAndTypeVariants();

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenWorkspaceSitesPageDtoWithNullFields_whenAsWorkspaceSitesPage_thenReturnWorkspaceSitesPageWithNullFields() {
        //given
        final WorkspaceSitesPageDTO responseDTO = this.getWorkspaceSitesPageDtoWithNullFields();
        final WorkspaceSitesPage expected = this.getWorkspaceSitesPageWithNullFields();

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullWorkspaceSitesPageDto_whenAsWorkspaceSitesPage_thenReturnNull() {
        //given

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(null);

        //then
        assertThat(actual).isNull();
    }

    private WorkspaceSitesPageDTO getWorkspaceSitesPageDto() {
        return new WorkspaceSitesPageDTO()
                .items(List.of(new WorkspaceSiteCardDTO()
                        .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                        .name("Agency Portfolio")
                        .status(WorkspaceSiteCardDTO.StatusEnum.DRAFT)
                        .type(WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO)
                        .description(null)
                        .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                        .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))))
                .page(0)
                .size(20)
                .hasNext(true);
    }

    private WorkspaceSitesPage getWorkspaceSitesPage() {
        return WorkspaceSitesPage.builder()
                .items(List.of(WorkspaceSiteCard.builder()
                        .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                        .name("Agency Portfolio")
                        .status(SiteStatus.DRAFT)
                        .type(SiteType.PORTFOLIO)
                        .description(null)
                        .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                        .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                        .build()))
                .page(0)
                .size(20)
                .hasNext(true)
                .build();
    }

    private WorkspaceSitesPageDTO getWorkspaceSitesPageDtoWithAllStatusAndTypeVariants() {
        return new WorkspaceSitesPageDTO()
                .items(List.of(
                        this.getWorkspaceSiteCardDto(
                                "00000000-0000-0000-0000-000000000001",
                                WorkspaceSiteCardDTO.StatusEnum.DRAFT,
                                WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO
                        ),
                        this.getWorkspaceSiteCardDto(
                                "00000000-0000-0000-0000-000000000002",
                                WorkspaceSiteCardDTO.StatusEnum.PUBLISHED,
                                WorkspaceSiteCardDTO.TypeEnum.BUSINESS
                        ),
                        this.getWorkspaceSiteCardDto(
                                "00000000-0000-0000-0000-000000000003",
                                WorkspaceSiteCardDTO.StatusEnum.ARCHIVED,
                                WorkspaceSiteCardDTO.TypeEnum.BLOG
                        ),
                        this.getWorkspaceSiteCardDto(
                                "00000000-0000-0000-0000-000000000004",
                                WorkspaceSiteCardDTO.StatusEnum.PUBLISHED,
                                WorkspaceSiteCardDTO.TypeEnum.STORE
                        ),
                        this.getWorkspaceSiteCardDto(
                                "00000000-0000-0000-0000-000000000005",
                                WorkspaceSiteCardDTO.StatusEnum.DRAFT,
                                WorkspaceSiteCardDTO.TypeEnum.LANDING
                        ),
                        this.getWorkspaceSiteCardDto(
                                "00000000-0000-0000-0000-000000000006",
                                WorkspaceSiteCardDTO.StatusEnum.PUBLISHED,
                                WorkspaceSiteCardDTO.TypeEnum.OTHER
                        ),
                        this.getWorkspaceSiteCardDto(
                                "00000000-0000-0000-0000-000000000007",
                                null,
                                null
                        )))
                .page(3)
                .size(20)
                .hasNext(false);
    }

    private WorkspaceSitesPage getWorkspaceSitesPageWithAllStatusAndTypeVariants() {
        return WorkspaceSitesPage.builder()
                .items(List.of(
                        this.getWorkspaceSiteCard("00000000-0000-0000-0000-000000000001", SiteStatus.DRAFT, SiteType.PORTFOLIO),
                        this.getWorkspaceSiteCard("00000000-0000-0000-0000-000000000002", SiteStatus.PUBLISHED, SiteType.BUSINESS),
                        this.getWorkspaceSiteCard("00000000-0000-0000-0000-000000000003", SiteStatus.ARCHIVED, SiteType.BLOG),
                        this.getWorkspaceSiteCard("00000000-0000-0000-0000-000000000004", SiteStatus.PUBLISHED, SiteType.STORE),
                        this.getWorkspaceSiteCard("00000000-0000-0000-0000-000000000005", SiteStatus.DRAFT, SiteType.LANDING),
                        this.getWorkspaceSiteCard("00000000-0000-0000-0000-000000000006", SiteStatus.PUBLISHED, SiteType.OTHER),
                        this.getWorkspaceSiteCard("00000000-0000-0000-0000-000000000007", null, null)))
                .page(3)
                .size(20)
                .hasNext(false)
                .build();
    }

    private WorkspaceSitesPageDTO getWorkspaceSitesPageDtoWithNullFields() {
        return new WorkspaceSitesPageDTO()
                .items(null)
                .page(1)
                .size(null)
                .hasNext(true);
    }

    private WorkspaceSitesPage getWorkspaceSitesPageWithNullFields() {
        return WorkspaceSitesPage.builder()
                .items(null)
                .page(1)
                .size(null)
                .hasNext(true)
                .build();
    }

    private WorkspaceSiteCardDTO getWorkspaceSiteCardDto(
            final String siteId,
            final WorkspaceSiteCardDTO.StatusEnum status,
            final WorkspaceSiteCardDTO.TypeEnum type
    ) {
        return new WorkspaceSiteCardDTO()
                .siteId(UUID.fromString(siteId))
                .name("Agency Portfolio")
                .status(status)
                .type(type)
                .description("Description")
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"));
    }

    private WorkspaceSiteCard getWorkspaceSiteCard(
            final String siteId,
            final SiteStatus status,
            final SiteType type
    ) {
        return WorkspaceSiteCard.builder()
                .siteId(UUID.fromString(siteId))
                .name("Agency Portfolio")
                .status(status)
                .type(type)
                .description("Description")
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                .build();
    }
}
