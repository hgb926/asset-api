package com.project.api.dto.response;

import com.project.api.entity.Board;
import com.project.api.entity.Reply;
import com.project.api.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private String createdAt;
    private Integer replyCount;
    private List<Reply> replies;
    private Long viewCount;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.author = board.getUser().getNickname();
        this.authorId = board.getUser().getId();
        this.category = board.getCategory().toString();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.replyCount = board.getReplies().size();
        this.replies = board.getReplies();
        this.viewCount = board.getViewCount();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.createdAt = board.getCreatedAt().format(formatter);

    }
}
