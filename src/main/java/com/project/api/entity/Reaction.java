package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"user", "board", "reply"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    @JsonIgnore
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @Enumerated(EnumType.STRING)
    private ReactionTargetType targetType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum ReactionType {
        LIKE, DISLIKE
    }

    public enum ReactionTargetType {
        BOARD,
        REPLY
    }

    // 명시적으로 관계 해제
    public void removeAssociations() {
        if (this.board != null) {
            this.board.getReactions().remove(this);
            this.board = null;
        }
        if (this.reply != null) {
            this.reply.getReactions().remove(this);
            this.reply = null;
        }
        if (this.user != null) {
            this.user.getReactions().remove(this);
            this.user = null;
        }
    }
}