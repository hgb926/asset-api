package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.Convert;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "user")
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
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reaction> reactions = new ArrayList<>();

    private Long viewCount;

    @PrePersist
    public void prePersist() {
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }
    }

    public enum Category {
        QNA, TIP, INFO
    }

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

}