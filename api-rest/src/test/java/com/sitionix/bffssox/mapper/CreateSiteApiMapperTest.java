package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.CreateSiteRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateSiteResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import com.sitionix.bffssox.domain.SiteStatus;
import com.sitionix.bffssox.domain.SiteTemplate;
import com.sitionix.bffssox.domain.SiteType;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateSiteApiMapperTest {

    private CreateSiteApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CreateSiteApiMapperImpl();
    }

    @Test
    void givenCreateSiteRequestDto_whenAsCreateSiteRequest_thenReturnCreateSiteRequest() {
        //given
        final CreateSiteRequestDTO createSiteRequestDTO = this.createSiteRequestDTO();
        final CreateSiteRequest expected = this.createSiteRequest();

        //when
        final CreateSiteRequest actual = this.mapper.asCreateSiteRequest(createSiteRequestDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenCreateSiteResponse_whenAsCreateSiteResponseDto_thenReturnCreateSiteResponseDto() {
        //given
        final CreateSiteResponse createSiteResponse = this.createSiteResponse();
        final CreateSiteResponseDTO expected = this.createSiteResponseDTO();

        //when
        final CreateSiteResponseDTO actual = this.mapper.asCreateSiteResponseDto(createSiteResponse);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private CreateSiteRequestDTO createSiteRequestDTO() {
        return CreateSiteRequestDTO.builder()
                .name("My portfolio")
                .type(CreateSiteRequestDTO.TypeEnum.PORTFOLIO)
                .description("Personal portfolio website")
                .template(CreateSiteRequestDTO.TemplateEnum.BLANK)
                .build();
    }

    private CreateSiteRequest createSiteRequest() {
        return CreateSiteRequest.builder()
                .name("My portfolio")
                .type(SiteType.PORTFOLIO)
                .description("Personal portfolio website")
                .template(SiteTemplate.BLANK)
                .build();
    }

    private CreateSiteResponse createSiteResponse() {
        return CreateSiteResponse.builder()
                .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .name("My portfolio")
                .status(SiteStatus.DRAFT)
                .createdAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .build();
    }

    private CreateSiteResponseDTO createSiteResponseDTO() {
        return CreateSiteResponseDTO.builder()
                .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .name("My portfolio")
                .status(CreateSiteResponseDTO.StatusEnum.DRAFT)
                .createdAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .build();
    }
}
