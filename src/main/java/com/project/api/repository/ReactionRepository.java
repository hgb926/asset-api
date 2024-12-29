package com.project.api.repository;

import com.project.api.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    boolean existsByUserIdAndReplyIdAndReactionType(Long userId, Long replyId, Reaction.ReactionType reactionType);

    boolean existsByUserIdAndBoardIdAndReactionType(Long userId, Long boardId, Reaction.ReactionType reactionType);

    Reaction findByUserIdAndReplyIdAndReactionType(Long userId, Long replyId, Reaction.ReactionType reactionType);

    Reaction findByUserIdAndBoardIdAndReactionType(Long userId, Long boardId, Reaction.ReactionType reactionType);
}
