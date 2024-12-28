package com.project.api.dto.request;

import com.project.api.entity.Board;
import com.project.api.entity.Board.Category;
import com.project.api.entity.User;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardSaveDto {

    private Long userId;

    private String title;

    private String content;

    private Category category;
}
