package com.github.ricardobaumann.jwtstarter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class JwtConfig {

    @Bean
    JwtParser jwtParser(@Value("${commercial.vi.secret}") String viSecret) {
        return Jwts.parser().setSigningKey(viSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    SessionInfoRepo sessionInfoRepo(JwtParser jwtParser) {
        return new SessionInfoRepo(jwtParser);
    }

    @Bean
    AuthHandler authHandler(SessionInfoRepo sessionInfoRepo) {
        return new AuthHandler(sessionInfoRepo);
    }
}
