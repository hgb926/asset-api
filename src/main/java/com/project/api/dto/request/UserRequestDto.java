package com.project.api.dto.request;

import com.project.api.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 로그인 성공 시 클라이언트에게 제공할 dto
public class UserRequestDto {

    private Long id;
    private String email;
    private String nickname;
    private boolean autoLogin;
    private String role;
    private LocalDateTime createdAt;
    private List<ChallengeParticipant> challenges;
    private List<Notice> noticeList;
    private AccountBook accountBook;
    private List<Goal> goalList;


}
