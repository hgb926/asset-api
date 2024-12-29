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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    // 처음 리액션을 한다? -> 좋아요든 싫어요든 INSERT
    // 기존 리액션을 취소한다? -> 기존 데이터를 DELETE
    // 기존 리액션을 변경한다?
    // -> 기존 리액션 데이터를 DELETE 후 새로운 리액션을 INSERT

    // 현재 게시물에 특정 사용자가 리액션을 했는지 확인

    public Reaction saveReaction(ReactionSaveDto dto) {

        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow();
        log.info("Reactions : {}", foundUser.getReactions());
        // 유저쪽은 아직 저장 안함

        Reaction newReaction = Reaction.builder()
                .reactionType(dto.getReactionType())
                .targetType(dto.getTargetType())
                .user(foundUser)
                .build();

        if (dto.getTargetType() == Reaction.ReactionTargetType.REPLY) {

            if (reactionRepository.existsByUserIdAndReplyId(dto.getUserId(), dto.getReplyId())) {
                // 이미 존재하는 경우
                log.info("이미 있음");
                return null;
            }

            Reply targetReply = replyRepository.findById(dto.getReplyId()).orElseThrow();
            newReaction.setReply(targetReply);
            replyRepository.save(targetReply);
        } else if (dto.getTargetType() == Reaction.ReactionTargetType.BOARD) {

            if (reactionRepository.existsByUserIdAndBoardId(dto.getUserId(), dto.getBoardId())) {
                // 이미 존재하는 경우
                log.info("이미 있음");
                return null;
            }
            Board targetBoard = boardRepository.findById(dto.getBoardId()).orElseThrow();
            newReaction.setBoard(targetBoard);
            boardRepository.save(targetBoard);
        }
        log.info("new Reaction : {} ", newReaction);
        reactionRepository.save(newReaction);
        return newReaction;
    }
}
