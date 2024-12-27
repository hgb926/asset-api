package com.project.api.dto.request;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplySaveDto {

    private Long userId;
    private Long boardId;
    private String content;


}
