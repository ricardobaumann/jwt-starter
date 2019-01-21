package com.github.ricardobaumann.jwtstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Order(0)
@Slf4j
public class AuthHandler implements HandlerInterceptor {

    private final SessionInfoRepo sessionInfoRepo;

    AuthHandler(SessionInfoRepo sessionInfoRepo) {
        this.sessionInfoRepo = sessionInfoRepo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        request.setAttribute("sessionInfo", parseSession(request)
                .orElseThrow(AuthenticationException::new));

        return true;
    }

    private Optional<SessionInfo> parseSession(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .map(sessionInfoRepo::getSessionInfo)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }


}
