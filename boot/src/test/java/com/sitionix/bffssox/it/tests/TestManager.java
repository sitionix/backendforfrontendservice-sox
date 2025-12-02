package com.sitionix.bffssox.it.tests;

import com.sitionix.forgeit.core.annotation.ForgeFeatures;
import com.sitionix.forgeit.core.api.ForgeIT;
import com.sitionix.forgeit.mockmvc.api.MockMvcSupport;
import com.sitionix.forgeit.wiremock.api.WireMockSupport;

@ForgeFeatures({WireMockSupport.class, MockMvcSupport.class})
public interface TestManager extends ForgeIT {
}
