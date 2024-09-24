package com.api.project.domain.auth.controller.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenRefreshReq {
    private String refreshToken;
}
