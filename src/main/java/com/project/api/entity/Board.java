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
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;  // 이제 API 만들면 됨

    private Category category;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @JsonManagedReference("board-reply")
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    private Long viewCount;

    // 좋아요 해야함
    @PrePersist
    public void prePersist() {
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }
    }

    public enum Category {
        QNA, TIP, INFO
    }

}