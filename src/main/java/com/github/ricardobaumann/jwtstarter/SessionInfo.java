package com.github.ricardobaumann.jwtstarter;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@ToString
public class SessionInfo {

    @NotNull
    private Long customerId;

    @NonNull
    private Long dealerUserId;

    private String authHeader;
}
