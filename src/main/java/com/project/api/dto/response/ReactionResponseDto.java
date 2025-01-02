package com.project.api.dto.response;

import com.project.api.entity.Reaction;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionResponseDto {

    private Long boardId;
    private Long replyId;
    private Long userId;
    private String reactionType;

    public ReactionResponseDto(Reaction reaction) {
        if (reaction.getBoard() != null) {
            this.boardId = reaction.getBoard().getId();
            this.replyId = 0L;
        } else if (reaction.getReply() != null) {
            this.replyId = reaction.getReply().getId();
            this.boardId = 0L;
        }
        this.userId = reaction.getUser().getId();
        this.reactionType = reaction.getReactionType().toString();
    }
}
