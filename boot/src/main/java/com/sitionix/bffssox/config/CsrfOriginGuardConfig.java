package com.sitionix.bffssox.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CsrfOriginGuardConfig implements WebMvcConfigurer {

    private final CsrfOriginGuardProps props;
    private final CsrfOriginGuardInterceptor csrfOriginGuardInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        if (!this.props.enabled()) {
            return;
        }
        registry.addInterceptor(this.csrfOriginGuardInterceptor)
                .addPathPatterns(this.props.protectedPaths().toArray(new String[0]));
    }
}
