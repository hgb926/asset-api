package com.project.api.dto.request;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    private String email;
    private String password;
    private boolean autoLogin;

    public LoginRequestDto(String email, String password, boolean autoLogin) {
        this.email = email;
        this.password = password;
        this.autoLogin = autoLogin;
    }
    // 등등.. 추가
}
