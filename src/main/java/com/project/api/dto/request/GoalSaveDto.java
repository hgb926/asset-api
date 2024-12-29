package com.project.api.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalSaveDto {

    private Long userId;
    private String category;
    private String description;
    private String type;
    private Long targetAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
