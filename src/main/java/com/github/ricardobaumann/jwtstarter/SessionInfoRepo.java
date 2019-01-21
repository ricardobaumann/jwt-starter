package com.github.ricardobaumann.jwtstarter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SessionInfoRepo {

    private static final Pattern AUTH_HEADER_TOKEN =
            Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE);
    private final JwtParser jwtParser;

    public SessionInfoRepo(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    Optional<SessionInfo> getSessionInfo(String authorization) {
        Matcher tokenMatcher = AUTH_HEADER_TOKEN.matcher(authorization);
        if (tokenMatcher.matches()) {
            String token = tokenMatcher.group(1);
            try {
                return Optional.ofNullable(jwtParser.parseClaimsJws(token).getBody())
                        .map(claims -> {
                            Long customerId = getCustomerId(claims);
                            return new SessionInfo(customerId,
                                    getDealerUserId(claims),
                                    token);
                        });
            } catch (MalformedJwtException e) {
                log.error("malformed header", e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Long getCustomerId(Claims claims) {
        return Optional.ofNullable(claims.get("custid"))
                .map(Object::toString)
                .map(Long::parseLong)
                .orElse(null);
    }

    private Long getDealerUserId(Claims claims) {
        return Optional.ofNullable(claims.getSubject())
                .map(Long::valueOf)
                .orElse(null);
    }

}
