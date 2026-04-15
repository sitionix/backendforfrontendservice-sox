package com.sitionix.bffssox.mapper;

import com.app_afesox.stsssox.client.dto.CreateSiteRequestDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteResponseDTO;
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
class CreateSiteClientMapperTest {

    private CreateSiteClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new CreateSiteClientMapperImpl();
    }

    @Test
    void givenCreateSiteRequest_whenAsCreateSiteRequestDto_thenReturnCreateSiteRequestDto() {
        //given
        final CreateSiteRequest createSiteRequest = this.createSiteRequest();
        final CreateSiteRequestDTO expected = this.createSiteRequestDto();

        //when
        final CreateSiteRequestDTO actual = this.mapper.asCreateSiteRequestDto(createSiteRequest);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenCreateSiteResponseDto_whenAsCreateSiteResponse_thenReturnCreateSiteResponse() {
        //given
        final CreateSiteResponseDTO createSiteResponseDTO = this.createSiteResponseDto();
        final CreateSiteResponse expected = this.createSiteResponse();

        //when
        final CreateSiteResponse actual = this.mapper.asCreateSiteResponse(createSiteResponseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullInputs_whenMap_thenReturnNull() {
        //given
        final CreateSiteRequest createSiteRequest = null;
        final CreateSiteResponseDTO createSiteResponseDTO = null;

        //when
        final CreateSiteRequestDTO actualRequest = this.mapper.asCreateSiteRequestDto(createSiteRequest);
        final CreateSiteResponse actualResponse = this.mapper.asCreateSiteResponse(createSiteResponseDTO);

        //then
        assertThat(actualRequest).isNull();
        assertThat(actualResponse).isNull();
    }

    @Test
    void givenOtherTypeAndArchivedStatus_whenMap_thenReturnMappedEnums() {
        //given
        final CreateSiteRequest createSiteRequest = CreateSiteRequest.builder()
                .name("My site")
                .type(SiteType.OTHER)
                .description("d")
                .template(SiteTemplate.BLANK)
                .build();
        final CreateSiteResponseDTO createSiteResponseDTO = new CreateSiteResponseDTO()
                .siteId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("My site")
                .status(CreateSiteResponseDTO.StatusEnum.ARCHIVED)
                .createdAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"));

        //when
        final CreateSiteRequestDTO actualRequest = this.mapper.asCreateSiteRequestDto(createSiteRequest);
        final CreateSiteResponse actualResponse = this.mapper.asCreateSiteResponse(createSiteResponseDTO);

        //then
        assertThat(actualRequest.getType()).isEqualTo(CreateSiteRequestDTO.TypeEnum.OTHER);
        assertThat(actualResponse.getStatus()).isEqualTo(SiteStatus.ARCHIVED);
    }

    @Test
    void givenAllEnums_whenMap_thenReturnMappedEnums() {
        //given
        final CreateSiteRequest baseRequest = CreateSiteRequest.builder()
                .name("n")
                .description("d")
                .template(SiteTemplate.BLANK)
                .build();

        //when
        for (final SiteType siteType : SiteType.values()) {
            final CreateSiteRequest request = CreateSiteRequest.builder()
                    .name(baseRequest.getName())
                    .description(baseRequest.getDescription())
                    .template(baseRequest.getTemplate())
                    .type(siteType)
                    .build();
            final CreateSiteRequestDTO actualRequest = this.mapper.asCreateSiteRequestDto(request);
            assertThat(actualRequest.getType().name()).isEqualTo(siteType.name());
        }
        for (final CreateSiteResponseDTO.StatusEnum statusEnum : CreateSiteResponseDTO.StatusEnum.values()) {
            final CreateSiteResponseDTO responseDTO = new CreateSiteResponseDTO()
                    .siteId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .name("n")
                    .status(statusEnum)
                    .createdAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                    .updatedAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"));
            final CreateSiteResponse actualResponse = this.mapper.asCreateSiteResponse(responseDTO);
            assertThat(actualResponse.getStatus().name()).isEqualTo(statusEnum.name());
        }

        //then
        assertThat(CreateSiteRequestDTO.TemplateEnum.BLANK.name()).isEqualTo(SiteTemplate.BLANK.name());
    }

    private CreateSiteRequest createSiteRequest() {
        return CreateSiteRequest.builder()
                .name("My portfolio")
                .type(SiteType.PORTFOLIO)
                .description("Personal portfolio website")
                .template(SiteTemplate.BLANK)
                .build();
    }

    private CreateSiteRequestDTO createSiteRequestDto() {
        return new CreateSiteRequestDTO()
                .name("My portfolio")
                .type(CreateSiteRequestDTO.TypeEnum.PORTFOLIO)
                .description("Personal portfolio website")
                .template(CreateSiteRequestDTO.TemplateEnum.BLANK);
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

    private CreateSiteResponseDTO createSiteResponseDto() {
        return new CreateSiteResponseDTO()
                .siteId(UUID.fromString("c9b1f3f4-12c7-11ec-82a8-0242ac130003"))
                .name("My portfolio")
                .status(CreateSiteResponseDTO.StatusEnum.DRAFT)
                .createdAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"));
    }
}
