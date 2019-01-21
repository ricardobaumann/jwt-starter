package com.github.ricardobaumann.jwtstarter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;
import java.util.Optional;

@Configuration
public class EnableJwtAuthImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(
                        annotationMetadata.getAnnotationAttributes
                                (EnableJwtAuth.class.getName(), false));
        String[] excludedUrls = Objects.requireNonNull(attributes).getStringArray("value");
        Optional.ofNullable(excludedUrls)
                .ifPresent(strings -> System.setProperty("excludeUrls", String.join(",", strings)));
        return new String[]{JwtConfig.class.getName(), MvcConfigurer.class.getName()};
    }

}
