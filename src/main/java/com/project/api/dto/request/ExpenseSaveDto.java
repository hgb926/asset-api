package com.project.api.dto.request;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseSaveDto {
    private String category;
    private Long userId;
    private Long amount;
    private String description;
}
