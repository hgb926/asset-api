package com.project.api.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 인증에 관련된 dto
public class LoginResponseDto {
    private String email;
    private String role; // 권한
    private String token; // 인증 토큰
    private String userId;
    private boolean autoLogin;

}
