package com.project.api.dto.request;

import com.project.api.entity.*;
import lombok.*;

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
    private String createdAt;
    private List<ChallengeParticipant> challenges;
    private List<Notice> noticeList;
    private List<Expense> expenseList;
    private List<Income> incomeList;
    private List<Goal> goalList;


}
