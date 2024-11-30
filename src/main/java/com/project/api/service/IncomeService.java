package com.project.api.service;

import com.project.api.dto.request.IncomeSaveDto;
import com.project.api.entity.Income;
import com.project.api.entity.User;
import com.project.api.repository.IncomeRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;


    public Income addIncome(IncomeSaveDto dto) {
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(null);
        foundUser.setCurrentMoney(foundUser.getCurrentMoney() + dto.getAmount());
        userRepository.save(foundUser);

        Income newIncome = Income.builder()
                .user(foundUser)
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();
        foundUser.getIncomeList().add(newIncome);
        log.info("new income obj - {}", newIncome);
        return newIncome;
        // account_book_id default value 문제

    }

    public Income modifyIncome(Long incomeId, IncomeSaveDto dto) {
        Income foundIc = incomeRepository.findById(incomeId).orElseThrow(null);
        foundIc.setAmount(dto.getAmount());
        foundIc.setCategory(dto.getCategory());
        foundIc.setDescription(dto.getDescription());
        incomeRepository.save(foundIc);
        return foundIc;
    }

    public void deleteIncome(Long incomeId, Long userId) {
        Income foundIc = incomeRepository.findById(incomeId).orElseThrow(null);
        incomeRepository.delete(foundIc);
        User foundUser = userRepository.findById(userId).orElseThrow(null);
        foundUser.setCurrentMoney(foundUser.getCurrentMoney() - foundIc.getAmount());
    }
}
