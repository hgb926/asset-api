package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-notice")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private String message;

    private boolean isClicked;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Long boardId;

    private Long goalId;

    public enum Type {
        목표, 커뮤니티, 알림
    }
}
