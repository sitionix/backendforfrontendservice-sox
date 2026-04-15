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

    @Test
    void givenNullInputs_whenMap_thenReturnNull() {
        //given
        final CreateSiteRequestDTO createSiteRequestDTO = null;
        final CreateSiteResponse createSiteResponse = null;

        //when
        final CreateSiteRequest actualRequest = this.mapper.asCreateSiteRequest(createSiteRequestDTO);
        final CreateSiteResponseDTO actualResponse = this.mapper.asCreateSiteResponseDto(createSiteResponse);

        //then
        assertThat(actualRequest).isNull();
        assertThat(actualResponse).isNull();
    }

    @Test
    void givenAllTypeAndStatusVariants_whenMap_thenReturnMappedVariants() {
        //given
        final CreateSiteRequestDTO requestDTO = CreateSiteRequestDTO.builder()
                .name("n")
                .type(CreateSiteRequestDTO.TypeEnum.OTHER)
                .description("d")
                .template(CreateSiteRequestDTO.TemplateEnum.BLANK)
                .build();
        final CreateSiteResponse response = CreateSiteResponse.builder()
                .siteId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("s")
                .status(SiteStatus.ARCHIVED)
                .createdAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .build();

        //when
        final CreateSiteRequest request = this.mapper.asCreateSiteRequest(requestDTO);
        final CreateSiteResponseDTO responseDTO = this.mapper.asCreateSiteResponseDto(response);

        //then
        assertThat(request.getType()).isEqualTo(SiteType.OTHER);
        assertThat(request.getTemplate()).isEqualTo(SiteTemplate.BLANK);
        assertThat(responseDTO.getStatus()).isEqualTo(CreateSiteResponseDTO.StatusEnum.ARCHIVED);
    }

    @Test
    void givenAllEnums_whenMap_thenReturnMappedEnums() {
        //given
        final CreateSiteResponse baseResponse = CreateSiteResponse.builder()
                .siteId(UUID.fromString("00000000-0000-0000-0000-000000000010"))
                .name("s")
                .createdAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .updatedAt(OffsetDateTime.parse("2026-02-17T10:00:00Z"))
                .build();

        //when
        for (final CreateSiteRequestDTO.TypeEnum typeEnum : CreateSiteRequestDTO.TypeEnum.values()) {
            final CreateSiteRequestDTO requestDTO = CreateSiteRequestDTO.builder()
                    .name("n")
                    .type(typeEnum)
                    .description("d")
                    .template(CreateSiteRequestDTO.TemplateEnum.BLANK)
                    .build();
            final CreateSiteRequest actualRequest = this.mapper.asCreateSiteRequest(requestDTO);
            assertThat(actualRequest.getType().name()).isEqualTo(typeEnum.name());
        }
        for (final SiteStatus status : SiteStatus.values()) {
            final CreateSiteResponse response = CreateSiteResponse.builder()
                    .siteId(baseResponse.getSiteId())
                    .name(baseResponse.getName())
                    .status(status)
                    .createdAt(baseResponse.getCreatedAt())
                    .updatedAt(baseResponse.getUpdatedAt())
                    .build();
            final CreateSiteResponseDTO actualResponse = this.mapper.asCreateSiteResponseDto(response);
            assertThat(actualResponse.getStatus().name()).isEqualTo(status.name());
        }

        //then
        assertThat(CreateSiteRequestDTO.TemplateEnum.BLANK.name()).isEqualTo(SiteTemplate.BLANK.name());
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
