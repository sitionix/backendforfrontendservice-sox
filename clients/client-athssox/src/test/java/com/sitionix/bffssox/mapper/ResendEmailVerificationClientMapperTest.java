package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.ResendEmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ResendEmailVerificationClientMapperTest {

    private ResendEmailVerificationClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ResendEmailVerificationClientMapperImpl();
    }

    @Test
    void givenResendEmailVerificationResponseDto_whenAsResendEmailVerificationResponse_thenReturnResendEmailVerificationResponse() {
        //given
        final ResendEmailVerificationResponseDTO given = this.resendEmailVerificationResponseDTO();
        final ResendEmailVerificationResponse expected = this.resendEmailVerificationResponse();

        //when
        final ResendEmailVerificationResponse actual = this.mapper.asResendEmailVerificationResponse(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullResponseDto_whenAsResendEmailVerificationResponse_thenReturnNull() {
        //given
        final ResendEmailVerificationResponseDTO given = null;

        //when
        final ResendEmailVerificationResponse actual = this.mapper.asResendEmailVerificationResponse(given);

        //then
        assertThat(actual).isNull();
    }

    private ResendEmailVerificationResponseDTO resendEmailVerificationResponseDTO() {
        return new ResendEmailVerificationResponseDTO()
                .message("message");
    }

    private ResendEmailVerificationResponse resendEmailVerificationResponse() {
        return ResendEmailVerificationResponse.builder()
                .message("message")
                .build();
    }
}
