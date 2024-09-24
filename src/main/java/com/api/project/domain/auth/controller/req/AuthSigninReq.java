package com.api.project.domain.auth.controller.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSigninReq {
    @NotBlank(message = "ID는 비워둘 수 없습니다.")
    private String id;

    @NotBlank(message = "비밀번호는 비워둘 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String pw;
}
