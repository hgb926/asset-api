package com.project.api.service;

import com.project.api.dto.request.ImportSaveDto;
import com.project.api.entity.Import;
import com.project.api.entity.User;
import com.project.api.repository.ImportRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ImportService {

    private final ImportRepository importRepository;
    private final UserRepository userRepository;


    public void addImport(ImportSaveDto dto) {
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(null);
        foundUser.setCurrentMoney(foundUser.getCurrentMoney() + dto.getAmount());
        userRepository.save(foundUser);

        Import newImport = Import.builder()
                .user(foundUser)
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();
        foundUser.getImportList().add(newImport);
        log.info("new import obj - {}", newImport);

        // account_book_id default value 문제

    }
}
