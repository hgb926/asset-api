package com.project.api.dto.request;

import lombok.*;

import static com.project.api.entity.Notice.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeSaveDto {

    private Long userId;
    private String message;
    private Type type;
}
