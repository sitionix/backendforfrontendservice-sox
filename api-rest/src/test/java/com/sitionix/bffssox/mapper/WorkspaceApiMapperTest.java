package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.WorkspaceSiteCardResponseDTO;
import com.app_afesox.bffssox.api_first.dto.WorkspaceSitesResponseDTO;
import com.sitionix.bffssox.domain.SiteStatus;
import com.sitionix.bffssox.domain.SiteType;
import com.sitionix.bffssox.domain.WorkspaceSiteCard;
import com.sitionix.bffssox.domain.WorkspaceSitesPage;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WorkspaceApiMapperTest {

    private WorkspaceApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new WorkspaceApiMapperImpl();
    }

    @Test
    void givenWorkspaceSitesPage_whenAsWorkspaceSitesResponseDto_thenReturnWorkspaceSitesResponseDto() {
        //given
        final WorkspaceSitesPage workspaceSitesPage = this.getWorkspaceSitesPage();
        final WorkspaceSitesResponseDTO expected = this.getWorkspaceSitesResponseDto();

        //when
        final WorkspaceSitesResponseDTO actual = this.mapper.asWorkspaceSitesResponseDto(workspaceSitesPage);

        //then
        assertThat(actual).isEqualTo(expected);
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

    private WorkspaceSitesResponseDTO getWorkspaceSitesResponseDto() {
        return new WorkspaceSitesResponseDTO()
                .items(List.of(new WorkspaceSiteCardResponseDTO()
                        .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                        .name("Agency Portfolio")
                        .status(WorkspaceSiteCardResponseDTO.StatusEnum.DRAFT)
                        .type(WorkspaceSiteCardResponseDTO.TypeEnum.PORTFOLIO)
                        .description(null)
                        .createdAt(OffsetDateTime.parse("2026-01-10T12:00:00Z"))
                        .updatedAt(OffsetDateTime.parse("2026-01-29T08:30:00Z"))))
                .page(0)
                .size(20)
                .hasNext(true);
    }
}
