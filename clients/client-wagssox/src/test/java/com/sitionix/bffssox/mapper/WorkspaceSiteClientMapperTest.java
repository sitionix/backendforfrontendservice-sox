package com.sitionix.bffssox.mapper;

import com.app_afesox.wagssox.client.dto.WorkspaceSiteCardDTO;
import com.app_afesox.wagssox.client.dto.WorkspaceSitesPageDTO;
import com.sitionix.bffssox.domain.SiteStatus;
import com.sitionix.bffssox.domain.SiteType;
import com.sitionix.bffssox.domain.WorkspaceSiteCard;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WorkspaceSiteClientMapperTest {

    private WorkspaceSiteClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new WorkspaceSiteClientMapperImpl();
    }

    @Test
    void givenWorkspaceSitesPageDto_whenAsWorkspaceSitesPage_thenReturnWorkspaceSitesPage() {
        //given
        final WorkspaceSitesPageDTO responseDTO = this.workspaceSitesPageDto(
                List.of(this.workspaceSiteCardDto(
                        "c9b1f3f4-12c7-11ec-82a8-0242ac130003",
                        WorkspaceSiteCardDTO.StatusEnum.DRAFT,
                        WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO,
                        null
                )),
                0,
                20,
                true
        );
        final WorkspaceSitesPage expected = this.workspaceSitesPage(
                List.of(this.workspaceSiteCard(
                        "c9b1f3f4-12c7-11ec-82a8-0242ac130003",
                        SiteStatus.DRAFT,
                        SiteType.PORTFOLIO,
                        null
                )),
                0,
                20,
                true
        );

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenWorkspaceSitesPageDtoWithAllStatusAndTypeVariants_whenAsWorkspaceSitesPage_thenReturnMappedWorkspaceSitesPage() {
        //given
        final WorkspaceSitesPageDTO responseDTO = this.workspaceSitesPageDto(
                List.of(
                        this.workspaceSiteCardDto("00000000-0000-0000-0000-000000000001",
                                WorkspaceSiteCardDTO.StatusEnum.DRAFT,
                                WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO,
                                "Description"),
                        this.workspaceSiteCardDto("00000000-0000-0000-0000-000000000002",
                                WorkspaceSiteCardDTO.StatusEnum.PUBLISHED,
                                WorkspaceSiteCardDTO.TypeEnum.BUSINESS,
                                "Description"),
                        this.workspaceSiteCardDto("00000000-0000-0000-0000-000000000003",
                                WorkspaceSiteCardDTO.StatusEnum.ARCHIVED,
                                WorkspaceSiteCardDTO.TypeEnum.BLOG,
                                "Description"),
                        this.workspaceSiteCardDto("00000000-0000-0000-0000-000000000004",
                                WorkspaceSiteCardDTO.StatusEnum.PUBLISHED,
                                WorkspaceSiteCardDTO.TypeEnum.STORE,
                                "Description"),
                        this.workspaceSiteCardDto("00000000-0000-0000-0000-000000000005",
                                WorkspaceSiteCardDTO.StatusEnum.DRAFT,
                                WorkspaceSiteCardDTO.TypeEnum.LANDING,
                                "Description"),
                        this.workspaceSiteCardDto("00000000-0000-0000-0000-000000000006",
                                WorkspaceSiteCardDTO.StatusEnum.PUBLISHED,
                                WorkspaceSiteCardDTO.TypeEnum.OTHER,
                                "Description"),
                        this.workspaceSiteCardDto("00000000-0000-0000-0000-000000000007",
                                null,
                                null,
                                "Description")
                ),
                3,
                20,
                false
        );
        final WorkspaceSitesPage expected = this.workspaceSitesPage(
                List.of(
                        this.workspaceSiteCard("00000000-0000-0000-0000-000000000001", SiteStatus.DRAFT, SiteType.PORTFOLIO, "Description"),
                        this.workspaceSiteCard("00000000-0000-0000-0000-000000000002", SiteStatus.PUBLISHED, SiteType.BUSINESS, "Description"),
                        this.workspaceSiteCard("00000000-0000-0000-0000-000000000003", SiteStatus.ARCHIVED, SiteType.BLOG, "Description"),
                        this.workspaceSiteCard("00000000-0000-0000-0000-000000000004", SiteStatus.PUBLISHED, SiteType.STORE, "Description"),
                        this.workspaceSiteCard("00000000-0000-0000-0000-000000000005", SiteStatus.DRAFT, SiteType.LANDING, "Description"),
                        this.workspaceSiteCard("00000000-0000-0000-0000-000000000006", SiteStatus.PUBLISHED, SiteType.OTHER, "Description"),
                        this.workspaceSiteCard("00000000-0000-0000-0000-000000000007", null, null, "Description")
                ),
                3,
                20,
                false
        );

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenWorkspaceSitesPageDtoWithNullFields_whenAsWorkspaceSitesPage_thenReturnWorkspaceSitesPageWithNullFields() {
        //given
        final WorkspaceSitesPageDTO responseDTO = this.workspaceSitesPageDto(null, 1, null, true);
        final WorkspaceSitesPage expected = this.workspaceSitesPage(null, 1, null, true);

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenWorkspaceSitesPageDtoWithNullItem_whenAsWorkspaceSitesPage_thenKeepNullItem() {
        //given
        final WorkspaceSitesPageDTO responseDTO = this.workspaceSitesPageDto(
                Arrays.asList(this.workspaceSiteCardDto(
                        "00000000-0000-0000-0000-000000000001",
                        WorkspaceSiteCardDTO.StatusEnum.DRAFT,
                        WorkspaceSiteCardDTO.TypeEnum.PORTFOLIO,
                        null
                ), null),
                0,
                20,
                true
        );

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(responseDTO);

        //then
        assertThat(actual.getItems()).hasSize(2);
        assertThat(actual.getItems().get(1)).isNull();
    }

    @Test
    void givenNullWorkspaceSitesPageDto_whenAsWorkspaceSitesPage_thenReturnNull() {
        //given

        //when
        final WorkspaceSitesPage actual = this.mapper.asWorkspaceSitesPage(null);

        //then
        assertThat(actual).isNull();
    }

    private WorkspaceSitesPageDTO workspaceSitesPageDto(
            final List<WorkspaceSiteCardDTO> items,
            final Integer page,
            final Integer size,
            final Boolean hasNext
    ) {
        return new WorkspaceSitesPageDTO()
                .items(items)
                .page(page)
                .size(size)
                .hasNext(hasNext);
    }

    private WorkspaceSitesPage workspaceSitesPage(
            final List<WorkspaceSiteCard> items,
            final Integer page,
            final Integer size,
            final Boolean hasNext
    ) {
        return WorkspaceSitesPage.builder()
                .items(items)
                .page(page)
                .size(size)
                .hasNext(hasNext)
                .build();
    }

    private WorkspaceSiteCardDTO workspaceSiteCardDto(
            final String siteId,
            final WorkspaceSiteCardDTO.StatusEnum status,
            final WorkspaceSiteCardDTO.TypeEnum type,
            final String description
    ) {
        return new WorkspaceSiteCardDTO()
                .siteId(UUID.fromString(siteId))
                .name("Agency Portfolio")
                .status(status)
                .type(type)
                .description(description)
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"));
    }

    private WorkspaceSiteCard workspaceSiteCard(
            final String siteId,
            final SiteStatus status,
            final SiteType type,
            final String description
    ) {
        return WorkspaceSiteCard.builder()
                .siteId(UUID.fromString(siteId))
                .name("Agency Portfolio")
                .status(status)
                .type(type)
                .description(description)
                .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))
                .build();
    }
}
