package com.sitionix.bffssox.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CsrfOriginGuardConfig implements WebMvcConfigurer {

    private final CsrfOriginGuardInterceptor csrfOriginGuardInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.csrfOriginGuardInterceptor)
                .addPathPatterns("/api/**");
    }
}
