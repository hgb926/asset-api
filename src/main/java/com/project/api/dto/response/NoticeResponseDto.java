package com.project.api.dto.response;

import com.project.api.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResponseDto {
    private Long id;
    private String type;
    private String message;
    private Long boardId;
    private Long goalId;
    private boolean isClicked;
    private String createdAt;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.type = notice.getType().toString();
        this.message = notice.getMessage();
        this.boardId = notice.getBoardId();
        this.goalId = notice.getGoalId();
        this.isClicked = notice.isClicked();
        this.createdAt = notice.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
