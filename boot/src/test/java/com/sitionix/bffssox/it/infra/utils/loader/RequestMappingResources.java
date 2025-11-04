package com.sitionix.bffssox.it.infra.utils.loader;

import org.springframework.stereotype.Component;

@Component
public class RequestMappingResources extends ResourceLoader {
    @Override
    protected String getResourcePath() {
        return "/given/mapping/request/%s";
    }
}
