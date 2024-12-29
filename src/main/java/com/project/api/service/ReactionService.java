package com.project.api.service;

import com.project.api.dto.request.ReactionSaveDto;
import com.project.api.entity.Board;
import com.project.api.entity.Reaction;
import com.project.api.entity.Reply;
import com.project.api.entity.User;
import com.project.api.repository.BoardRepository;
import com.project.api.repository.ReactionRepository;
import com.project.api.repository.ReplyRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.project.api.entity.Reaction.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * 리액션 저장 또는 삭제
     */
    public Reaction saveReaction(ReactionSaveDto dto) {
        User foundUser = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        log.info("Reactions: {}", foundUser.getReactions());

        Reaction newReaction = Reaction.builder()
                .reactionType(dto.getReactionType())
                .targetType(dto.getTargetType())
                .user(foundUser)
                .build();

        if (dto.getTargetType() == ReactionTargetType.REPLY) {
            return handleReplyReaction(dto, newReaction);
        } else if (dto.getTargetType() == ReactionTargetType.BOARD) {
            return handleBoardReaction(dto, newReaction);
        }

        throw new IllegalArgumentException("Invalid ReactionTargetType");
    }

    /**
     * 댓글 리액션 처리
     */
    private Reaction handleReplyReaction(ReactionSaveDto dto, Reaction newReaction) {
        boolean exists = reactionRepository.existsByUserIdAndReplyIdAndReactionType(
                dto.getUserId(), dto.getReplyId(), dto.getReactionType()
        );

        if (exists) {
            Reaction foundReaction = reactionRepository.findByUserIdAndReplyIdAndReactionType(
                    dto.getUserId(), dto.getReplyId(), dto.getReactionType()
            );
            reactionRepository.delete(foundReaction);
            entityManager.flush(); // 즉시 데이터베이스에 반영
            entityManager.clear(); // 영속성 컨텍스트 초기화
            log.info("Reaction deleted: {}", foundReaction);
            return null; // 동일한 리액션이라면 삭제만 수행
        }

        Reply targetReply = replyRepository.findById(dto.getReplyId())
                .orElseThrow(() -> new IllegalArgumentException("Reply not found"));

        newReaction.setReply(targetReply);
        log.info("New Reaction: {}", newReaction);
        return reactionRepository.save(newReaction);
    }

    /**
     * 게시글 리액션 처리
     */
    private Reaction handleBoardReaction(ReactionSaveDto dto, Reaction newReaction) {
        boolean exists = reactionRepository.existsByUserIdAndBoardIdAndReactionType(
                dto.getUserId(), dto.getBoardId(), dto.getReactionType()
        );

        if (exists) {
            Reaction foundReaction = reactionRepository.findByUserIdAndBoardIdAndReactionType(
                    dto.getUserId(), dto.getBoardId(), dto.getReactionType()
            );
            reactionRepository.delete(foundReaction);
            entityManager.flush(); // 즉시 데이터베이스에 반영
            entityManager.clear(); // 영속성 컨텍스트 초기화
            log.info("Reaction deleted: {}", foundReaction);
            return null; // 동일한 리액션이라면 삭제만 수행
        }

        Board targetBoard = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        newReaction.setBoard(targetBoard);
        log.info("New Reaction: {}", newReaction);
        return reactionRepository.save(newReaction);
    }

}