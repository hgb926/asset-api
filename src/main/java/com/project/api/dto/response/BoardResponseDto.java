package com.project.api.dto.response;

import com.project.api.entity.Board;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
    private Long id;
    private String author;
    private Long authorId;
    private String category;
    private String title;
    private String content;
    private String createdAt;
    private Integer replyCount;
    private List<ReplyResponseDto> replies; // Reply DTO 사용
    private List<ReactionResponseDto> reactions;
    private Long likeCount;
    private Long dislikeCount;
    private Long viewCount;
    private boolean isModified;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.author = board.getUser().getNickname();
        this.authorId = board.getUser().getId();
        this.category = board.getCategory().toString();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.replyCount = board.getReplies().size();
        this.replies = board.getReplies()
                .stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList()); // Reply 엔티티를 ReplyResponseDto로 변환
        this.reactions = board.getReactions()
                .stream()
                .map(ReactionResponseDto::new)
                .collect(Collectors.toList());
        this.likeCount = board.getLikeCount(board.getReactions());
        this.dislikeCount = board.getDislikeCount(board.getReactions());
        this.viewCount = board.getViewCount();
        this.createdAt = board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.isModified = board.isModified();
    }
}