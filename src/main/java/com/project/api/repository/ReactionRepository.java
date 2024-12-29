package com.project.api.repository;

import com.project.api.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    public boolean existsByUserIdAndBoardId(Long userId, Long boardId);
    public boolean existsByUserIdAndReplyId(Long userId, Long replyId);
}
