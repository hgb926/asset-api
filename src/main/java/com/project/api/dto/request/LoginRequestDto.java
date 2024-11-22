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
    private String nickname;
    private boolean autoLogin;

    public LoginRequestDto(String email, String password, String nickname,  boolean autoLogin) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.autoLogin = autoLogin;
    }
    // 등등.. 추가
}
