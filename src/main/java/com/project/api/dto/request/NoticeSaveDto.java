package com.project.api.dto.request;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeSaveDto {

    private Long userId;
    private String message;
}
