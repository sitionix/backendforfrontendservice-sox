package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.SiteClient;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateSiteImplTest {

    private CreateSite createSite;

    @Mock
    private SiteClient siteClient;

    @BeforeEach
    void setUp() {
        this.createSite = new CreateSiteImpl(this.siteClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.siteClient);
    }

    @Test
    void givenCreateSiteRequest_whenExecute_thenReturnCreateSiteResponse() {
        //given
        final CreateSiteRequest createSiteRequest = mock(CreateSiteRequest.class);
        final CreateSiteResponse createSiteResponse = mock(CreateSiteResponse.class);

        when(this.siteClient.createSite(createSiteRequest)).thenReturn(createSiteResponse);

        //when
        final CreateSiteResponse actual = this.createSite.execute(createSiteRequest);

        //then
        assertThat(actual).isEqualTo(createSiteResponse);

        verify(this.siteClient).createSite(createSiteRequest);
    }
}
