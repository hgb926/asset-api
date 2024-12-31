package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reaction> reactions = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Long getLikeCount(List<Reaction> reactions) {
        return reactions.stream().filter(r ->
                        r.getReactionType() == Reaction.ReactionType.LIKE)
                .count();
    }

    public Long getDislikeCount(List<Reaction> reactions) {
        return reactions.stream().filter(r ->
                        r.getReactionType() == Reaction.ReactionType.DISLIKE)
                .count();
    }

    // 연관관계 해제 메서드 추가
    public void removeAssociations() {
        this.reactions.forEach(reaction -> reaction.setReply(null));
        this.reactions.clear();
        this.board = null;
    }

}
