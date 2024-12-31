package com.project.api.dto.request;

import com.project.api.entity.Board;
import lombok.*;

import static com.project.api.entity.Board.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardModifyDto {
    private Long id;
    private Category category;
    private String title;
    private String content;
}
