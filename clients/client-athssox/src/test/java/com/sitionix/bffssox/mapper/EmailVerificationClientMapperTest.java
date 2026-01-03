package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.EmailVerificationDTO;
import com.app_afesox.athssox.client.dto.EmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import com.sitionix.bffssox.domain.EmailVerificationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EmailVerificationClientMapperTest {

    private EmailVerificationClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new EmailVerificationClientMapperImpl();
    }

    @Test
    void givenEmailVerificationRequest_whenAsEmailVerificationDto_thenReturnEmailVerificationDto() {
        //given
        final UUID uuid = UUID.randomUUID();
        final EmailVerificationRequest request = this.emailVerificationRequest(uuid);
        final EmailVerificationDTO expected = this.emailVerificationDTO(uuid);

        //when
        final EmailVerificationDTO actual = this.mapper.asEmailVerificationDto(request);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenEmailVerificationResponseDTO_whenAsEmailVerificationResponse_thenReturnEmailVerificationResponse() {
        //given
        final EmailVerificationResponseDTO responseDTO = this.emailVerificationResponseDTO();
        final EmailVerificationResponse expected = this.emailVerificationResponse();

        //when
        final EmailVerificationResponse actual = this.mapper.asEmailVerificationResponse(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private EmailVerificationDTO emailVerificationDTO(final UUID uuid) {
        return new EmailVerificationDTO()
                .token("token")
                .siteId(uuid);
    }

    private EmailVerificationRequest emailVerificationRequest(final UUID uuid) {
        return EmailVerificationRequest.builder()
                .token("token")
                .siteId(uuid)
                .build();
    }

    private EmailVerificationResponseDTO emailVerificationResponseDTO() {
        return new EmailVerificationResponseDTO()
                .message("message")
                .status(EmailVerificationResponseDTO.StatusEnum.ACTIVE);
    }

    private EmailVerificationResponse emailVerificationResponse() {
        return EmailVerificationResponse.builder()
                .message("message")
                .status(EmailVerificationStatus.ACTIVE)
                .build();
    }
}
