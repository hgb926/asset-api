package com.project.api.service;

import com.project.api.dto.request.UserSaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    @DisplayName("encoded 비번 받기")
    void getEncodedPassword() {
        //given
        for (int i = 10; i < 15; i++) {
            UserSaveDto built = UserSaveDto.builder()
                    .email("test" + i + "@naver.com")
                    .password("gksrlqja1!")
                    .nickname("테스트" + i)
                    .build();
            userService.confirmSignUp(built);
        }
        //when
        //then
    }


}