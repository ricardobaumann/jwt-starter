package com.github.ricardobaumann.jwtstarter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableJwtAuthImportSelector.class)
public @interface EnableJwtAuth {
    String[] value();
}
