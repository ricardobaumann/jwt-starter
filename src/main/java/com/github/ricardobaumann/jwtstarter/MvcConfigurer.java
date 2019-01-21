package com.github.ricardobaumann.jwtstarter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebMvc
public class MvcConfigurer implements WebMvcConfigurer {

    private final AuthHandler authHandler;

    public MvcConfigurer(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        Optional.ofNullable(System.getProperty("excludeUrls"))
                .ifPresent(urls -> {
                    String[] excludeUrls = urls.split(",");
                    registry.addInterceptor(authHandler).excludePathPatterns(excludeUrls);

                });

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SessionInfoArgumentResolver());
    }
}
