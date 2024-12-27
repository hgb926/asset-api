package com.project.api.dto.response;

import com.project.api.entity.Board;
import com.project.api.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
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
    private LocalDateTime createdAt;
    private Integer replyCount;
    private Long viewCount;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.author = board.getUser().getNickname();
        this.authorId = board.getUser().getId();
        this.category = board.getCategory().toString();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.replyCount = board.getReplyList().size();
        this.viewCount = board.getViewCount();

    }
}
