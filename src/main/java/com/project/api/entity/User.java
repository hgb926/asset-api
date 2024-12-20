package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String password;

    private String nickname;

    private Long currentMoney;

    private boolean autoLogin;

    private Role role;

    private boolean emailVerified;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @JsonManagedReference("user-challenge-participant")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeParticipant> challenges = new ArrayList<>();

    @Builder.Default
    @JsonManagedReference("user-notice")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> noticeList = new ArrayList<>();

//    // User 엔터티
//    @JsonManagedReference
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private AccountBook accountBooks;

    @Builder.Default
    @JsonManagedReference("user-expense")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenseList = new ArrayList<>();

    @Builder.Default
    @JsonManagedReference("user-import")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomeList = new ArrayList<>();

    @Builder.Default
    @JsonManagedReference("user-goal")
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = Role.USER;
        }
    }

    public void confirm(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
    // 이제 유저 조회 메서드 등등
}