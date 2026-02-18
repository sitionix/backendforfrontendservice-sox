package com.sitionix.bffssox.client;

import com.app_afesox.stsssox.client.api.SiteApi;
import com.app_afesox.stsssox.client.dto.CreateSiteRequestDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import com.sitionix.bffssox.mapper.CreateSiteClientMapper;
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SiteClientImplTest {

    private SiteClient siteClient;

    @Mock
    private SiteApi siteApi;

    @Mock
    private CreateSiteClientMapper createSiteClientMapper;

    @Mock
    private StsssoxClientCallExecutor stsssoxClientCallExecutor;

    @BeforeEach
    void setUp() {
        this.siteClient = new SiteClientImpl(this.siteApi,
                this.createSiteClientMapper,
                this.stsssoxClientCallExecutor);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteApi,
                this.createSiteClientMapper,
                this.stsssoxClientCallExecutor);
    }

    @Test
    void givenCreateSiteRequest_whenCreateSite_thenReturnCreateSiteResponse() {
        //given
        final CreateSiteRequest request = org.mockito.Mockito.mock(CreateSiteRequest.class);
        final CreateSiteResponse response = org.mockito.Mockito.mock(CreateSiteResponse.class);
        final CreateSiteRequestDTO requestDTO = org.mockito.Mockito.mock(CreateSiteRequestDTO.class);
        final CreateSiteResponseDTO responseDTO = org.mockito.Mockito.mock(CreateSiteResponseDTO.class);

        when(this.createSiteClientMapper.asCreateSiteRequestDto(request)).thenReturn(requestDTO);
        when(this.createSiteClientMapper.asCreateSiteResponse(responseDTO)).thenReturn(response);
        when(this.stsssoxClientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<CreateSiteResponseDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.siteApi.createSite(requestDTO)).thenReturn(responseDTO);

        //when
        final CreateSiteResponse actual = this.siteClient.createSite(request);

        //then
        assertThat(actual).isEqualTo(response);

        verify(this.createSiteClientMapper).asCreateSiteRequestDto(request);
        verify(this.stsssoxClientCallExecutor).execute(any());
        verify(this.siteApi).createSite(requestDTO);
        verify(this.createSiteClientMapper).asCreateSiteResponse(responseDTO);
    }
}
