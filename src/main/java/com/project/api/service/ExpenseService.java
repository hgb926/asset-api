package com.project.api.service;

import com.project.api.dto.request.ExpenseSaveDto;
import com.project.api.entity.Expense;
import com.project.api.entity.User;
import com.project.api.repository.ExpenseRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public Expense addExpense(ExpenseSaveDto dto) {
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(null);
        foundUser.setCurrentMoney(foundUser.getCurrentMoney() - dto.getAmount());
        userRepository.save(foundUser);

        Expense newExpense = Expense.builder()
                .user(foundUser)
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();
        foundUser.getExpenseList().add(newExpense);
        log.info("new expense obj - {} ", newExpense);

        expenseRepository.save(newExpense);
        return newExpense;
    }

    public Expense modifyExpense(Long expenseId, ExpenseSaveDto dto) {
        Expense foundEx = expenseRepository.findById(expenseId).orElseThrow(null);
        foundEx.setAmount(dto.getAmount());
        foundEx.setCategory(dto.getCategory());
        foundEx.setDescription(dto.getDescription());
        expenseRepository.save(foundEx);
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(null);
                                // 원래 돈 + 수정 전 금액 - 수정 후 금액
        foundUser.setCurrentMoney(foundUser.getCurrentMoney() + foundEx.getAmount() - dto.getAmount());
        userRepository.save(foundUser);

        return foundEx;
    }

    public void deleteExpense(Long expenseId, Long userId) {
        Expense foundEx = expenseRepository.findById(expenseId).orElseThrow(null);
        expenseRepository.delete(foundEx);
        User foundUser = userRepository.findById(userId).orElseThrow(null);
        foundUser.setCurrentMoney(foundUser.getCurrentMoney() + foundEx.getAmount());
    }
}
