package com.sitionix.bffssox.config;

import com.sitionix.bffssox.security.BffCsrfFilter;
import com.sitionix.bffssox.security.BffSessionAuthenticationFilter;
import com.sitionix.forge.security.userjwt.web.ForgeUserJwtFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http,
                                                   final ObjectProvider<ForgeUserJwtFilter> forgeUserJwtFilterProvider,
                                                   final BffSessionAuthenticationFilter bffSessionAuthenticationFilter,
                                                   final BffCsrfFilter bffCsrfFilter,
                                                   final ObjectProvider<AuthenticationEntryPoint> entryPointProvider,
                                                   final ObjectProvider<AccessDeniedHandler> accessDeniedHandlerProvider)
            throws Exception {
        final ForgeUserJwtFilter forgeUserJwtFilter = forgeUserJwtFilterProvider.getIfAvailable();
        final AuthenticationEntryPoint entryPoint = entryPointProvider.getIfAvailable();
        final AccessDeniedHandler accessDeniedHandler = accessDeniedHandlerProvider.getIfAvailable();
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        if (forgeUserJwtFilter != null) {
            http.addFilterBefore(forgeUserJwtFilter, AuthorizationFilter.class)
                    .addFilterAfter(bffSessionAuthenticationFilter, ForgeUserJwtFilter.class);
        } else {
            http.addFilterBefore(bffSessionAuthenticationFilter, AuthorizationFilter.class);
        }
        http.addFilterAfter(bffCsrfFilter, BffSessionAuthenticationFilter.class);
        if (entryPoint != null || accessDeniedHandler != null) {
            http.exceptionHandling(exceptionHandling -> {
                if (entryPoint != null) {
                    exceptionHandling.authenticationEntryPoint(entryPoint);
                }
                if (accessDeniedHandler != null) {
                    exceptionHandling.accessDeniedHandler(accessDeniedHandler);
                }
            });
        }
        return http.build();
    }
}
