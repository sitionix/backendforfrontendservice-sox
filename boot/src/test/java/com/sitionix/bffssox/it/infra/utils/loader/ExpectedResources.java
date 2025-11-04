package com.sitionix.bffssox.it.infra.utils.loader;

import org.springframework.stereotype.Component;

@Component
public class ExpectedResources extends ResourceLoader {

    @Override
    protected String getResourcePath() {
        return "/expected/%s";
    }
}
