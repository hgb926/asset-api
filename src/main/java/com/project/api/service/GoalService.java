package com.project.api.service;

import com.project.api.dto.request.GoalSaveDto;
import com.project.api.entity.Goal;
import com.project.api.entity.User;
import com.project.api.repository.GoalRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public Goal addGoal(GoalSaveDto dto) {
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow();

        Long influencedMoney = calculateInfluencedMoney(foundUser, dto.getCategory(), dto.getType());

        // 진행률(currentProgress) 계산
        Long currentProgress = calculateProgress(influencedMoney, dto.getTargetAmount());

        Goal newGoal = Goal.builder()
                .category(dto.getCategory())
                .description(dto.getDescription())
                .endDate(dto.getEndDate())
                .startDate(dto.getStartDate())
                .targetAmount(dto.getTargetAmount())
                .type(dto.getType())
                .achieved(false)
                .currentProgress(currentProgress)
                .influencedMoney(influencedMoney)
                .user(foundUser)
                .build();

        goalRepository.save(newGoal);
        return newGoal;
    }

    /**
     * 목표 타입에 따라 influencedMoney 계산
     * - type이 'income'이면 사용자 수입 목록에서 해당 카테고리의 총합을 계산
     * - type이 'expense'면 사용자 지출 목록에서 해당 카테고리의 총합을 계산
     */
    private Long calculateInfluencedMoney(User user, String category, String type) {
        if ("income".equalsIgnoreCase(type)) {
            return user.getIncomeList().stream()
                    .filter(income -> income.getCategory().equals(category))
                    .mapToLong(income -> income.getAmount())
                    .sum();
        } else if ("expense".equalsIgnoreCase(type)) {
            return user.getExpenseList().stream()
                    .filter(expense -> expense.getCategory().equals(category))
                    .mapToLong(expense -> expense.getAmount())
                    .sum();
        } else {
            return 0L;
        }
    }

    /**
     * 목표 진행률(currentProgress) 계산
     */
    private Long calculateProgress(Long influencedMoney, Long targetAmount) {
        if (targetAmount == null || targetAmount == 0) {
            return 0L;
        }
        return Math.min(100, (influencedMoney * 100) / targetAmount); // 최대 진행률은 100%
    }
}
