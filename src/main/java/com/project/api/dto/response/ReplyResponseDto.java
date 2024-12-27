package com.project.api.dto.response;

import com.project.api.entity.Reply;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyResponseDto {
    private Long id;
    private String author;
    private String content;
    private String createdAt;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.author = reply.getUser().getNickname(); // Reply의 User에서 닉네임 추출
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}