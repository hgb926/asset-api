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

    public Reaction saveReaction(ReactionSaveDto dto) {

        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow();
        // 유저쪽은 아직 저장 안함

        Reaction newReaction = Reaction.builder()
                .reactionType(dto.getReactionType())
                .targetType(dto.getTargetType())
                .user(foundUser)
                .build();

        if (dto.getTargetType() == Reaction.ReactionTargetType.REPLY) {
            Reply targetReply = replyRepository.findById(dto.getReplyId()).orElseThrow();
            newReaction.setReply(targetReply);
            targetReply.getReactions().add(newReaction);
            replyRepository.save(targetReply);
        } else if (dto.getTargetType() == Reaction.ReactionTargetType.BOARD) {
            Board targetBoard = boardRepository.findById(dto.getBoardId()).orElseThrow();
            newReaction.setBoard(targetBoard);
            targetBoard.getReactions().add(newReaction);
            boardRepository.save(targetBoard);
        }
        log.info("new Reaction : {} ", newReaction);
        reactionRepository.save(newReaction);
        return newReaction;
    }
}
