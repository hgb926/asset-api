package com.project.api.entity;

import lombok.*;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String category;

    private String description;

    private Long targetAmount;

    private Long currentProgress;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(name = "achieved", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean achieved;

    @PrePersist
    public void prePersist() {
        if (!this.achieved) {
            this.achieved = false; // 기본값 설정
        }
    }
}
