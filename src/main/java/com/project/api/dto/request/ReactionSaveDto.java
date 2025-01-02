package com.project.api.dto.request;

import com.project.api.entity.Reaction;
import lombok.*;

import static com.project.api.entity.Reaction.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionSaveDto {

    @Setter
    private Long boardId;
    @Setter
    private Long replyId;
    private Long userId;
    private ReactionType reactionType; // like, dislike
    private ReactionTargetType targetType; // board, reply

}
