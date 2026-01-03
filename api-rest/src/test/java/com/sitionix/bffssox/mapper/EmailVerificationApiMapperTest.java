package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
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
class EmailVerificationApiMapperTest {

    private EmailVerificationApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new EmailVerificationApiMapperImpl();
    }

    @Test
    void givenEmailVerificationDto_whenAsEmailVerificationRequest_thenReturnEmailVerificationRequest() {
        //given
        final UUID uuid = UUID.randomUUID();
        final EmailVerificationRequest expected = this.emailVerificationRequest(uuid);
        final EmailVerificationDTO given = this.emailVerificationDTO(uuid);

        //when
        final EmailVerificationRequest actual = this.mapper.asEmailVerificationRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenEmailVerificationResponse_whenAsEmailVerificationResponseDto_thenReturnEmailVerificationResponseDto() {
        //given
        final EmailVerificationResponseDTO expected = this.emailVerificationResponseDTO();
        final EmailVerificationResponse given = this.emailVerificationResponse();

        //when
        final EmailVerificationResponseDTO actual = this.mapper.asEmailVerificationResponseDTO(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private EmailVerificationDTO emailVerificationDTO(final UUID uuid) {
        return EmailVerificationDTO.builder()
                .token("token")
                .siteId(uuid)
                .build();
    }

    private EmailVerificationRequest emailVerificationRequest(final UUID uuid) {
        return EmailVerificationRequest.builder()
                .token("token")
                .siteId(uuid)
                .build();
    }

    private EmailVerificationResponseDTO emailVerificationResponseDTO() {
        return EmailVerificationResponseDTO.builder()
                .message("message")
                .status(EmailVerificationResponseDTO.StatusEnum.ACTIVE)
                .build();
    }

    private EmailVerificationResponse emailVerificationResponse() {
        return EmailVerificationResponse.builder()
                .message("message")
                .status(EmailVerificationStatus.ACTIVE)
                .build();
    }
}
