package com.project.api.service;

import com.project.api.dto.request.BoardSaveDto;
import com.project.api.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("게시글 더미데이터 삽입")
    void addDummyData() {

        String[] titles = {
                "이번 달 예산이 자꾸 초과돼요. 어떻게 해야 할까요?", // 질문
                "현금영수증 꼭 발급받아야 할까요?", // 질문
                "저축이 너무 어려워요. 어디서부터 시작해야 하나요?", // 질문
                "생활비 줄이는 실전 팁 5가지!", // 꿀팁
                "가계부 쓰기 정말 효과가 있을까요?", // 질문
                "올해 연말정산 꿀팁 정리", // 꿀팁
                "하루 커피값 줄이는 현실적인 방법", // 꿀팁
                "매달 10만 원 저축, 가능할까요?", // 질문
                "소비 패턴 분석은 어떻게 해야 할까요?", // 질문
                "돈을 모으기 위한 필수 습관 3가지", // 꿀팁
                "적금 VS 예금, 뭐가 더 좋을까요?", // 질문
                "신용카드 포인트, 어디에 써야 가장 효율적일까요?", // 질문
                "자취생을 위한 생활비 절약 꿀팁", // 꿀팁
                "공과금 줄이는 방법 아시는 분 계신가요?", // 질문
                "재테크 초보자를 위한 가이드북", // 꿀팁
                "소액 투자, 어디서 시작해야 할까요?", // 질문
                "구독 서비스가 너무 많아요. 어떻게 줄일까요?", // 질문
                "비상금 통장은 꼭 필요할까요?", // 질문
                "최대 환급받는 연말정산 노하우", // 꿀팁
                "이번 달에 너무 많이 쓴 것 같아요. 어떻게 해야 할까요?", // 질문
                "매일 커피값 아껴보기 챌린지", // 꿀팁
                "금융상품 추천해주세요!", // 질문
                "가성비 좋은 보험 찾는 법", // 꿀팁
                "돈을 가장 쉽게 모을 수 있는 방법은 뭘까요?", // 질문
                "불필요한 지출을 확인하는 방법", // 꿀팁
                "생활비 줄이기 도전기", // 꿀팁
                "대학생 예산 관리 어떻게 해야 할까요?", // 질문
                "부동산 투자, 초보도 할 수 있을까요?", // 질문
                "카드사 할인 혜택 최대한 활용하는 법", // 꿀팁
                "이번 달 카드값이 너무 많이 나왔어요. 해결책이 있을까요?", // 질문
                "30일 동안 쇼핑 금지 챌린지!", // 꿀팁
                "세금 환급받는 확실한 방법", // 꿀팁
                "주식 시작하기 전에 알아야 할 것들", // 꿀팁
                "적금 목표를 세우는 방법은 무엇일까요?", // 질문
                "신용 점수 올리는 현실적인 방법", // 꿀팁
                "중고 거래, 안전하게 하는 방법이 있을까요?", // 질문
                "연금보험이 꼭 필요할까요?", // 질문
                "여행 예산을 아끼는 꿀팁 7가지", // 꿀팁
                "카드 리볼빙 서비스, 정말 유용할까요?", // 질문
                "나만의 저축 목표 설정하기", // 꿀팁
                "가장 효율적인 저축 방법 추천해주세요!" // 질문
        };

        String[] contents = {
                "한 달 예산을 지키기가 정말 어렵네요. 매번 초과되는데 좋은 방법 있을까요?", // 질문
                "현금영수증을 받으면 어떤 혜택이 있는지 궁금해요!", // 질문
                "저축 습관을 들이기 어려운데, 경험자분들 조언 부탁드립니다.", // 질문
                "일주일 예산을 정하고 초과하지 않는 방법, 꿀팁 드립니다.", // 꿀팁
                "가계부를 쓰고 있는데도 예산이 잘 맞지 않아요. 문제점이 뭘까요?", // 질문
                "올해 연말정산으로 얼마나 환급받을 수 있을지 미리 확인해보세요!", // 꿀팁
                "커피값을 줄이는 가장 좋은 방법을 공유해요.", // 꿀팁
                "적은 금액으로도 저축을 꾸준히 할 수 있는 방법이 있을까요?", // 질문
                "소비 패턴을 분석해서 예산을 절약해본 경험을 공유해요.", // 질문
                "저축을 습관화하는 방법, 3단계로 정리해드립니다!", // 꿀팁
                "적금과 예금 중 어떤 게 더 나은 선택인지 궁금해요.", // 질문
                "신용카드 포인트를 효율적으로 사용하는 방법을 알려주세요.", // 질문
                "혼자 사는 사람들을 위한 필수 절약 팁을 알려드립니다!", // 꿀팁
                "공과금을 줄일 수 있는 팁이 있다면 공유해주세요.", // 질문
                "재테크를 처음 시작하는 사람들을 위한 단계별 가이드입니다.", // 꿀팁
                "소액 투자는 어떻게 시작해야 할지 막막하네요.", // 질문
                "필요 없는 구독 서비스가 너무 많아요. 정리하는 팁 있을까요?", // 질문
                "비상금 통장은 꼭 필요할까요? 어떻게 관리해야 할까요?", // 질문
                "연말정산으로 최대한 환급받는 방법을 정리했습니다!", // 꿀팁
                "예산을 지키는 게 너무 어려워요. 좋은 방법 없을까요?", // 질문
                "커피값을 절약하는 챌린지에 도전해보세요!", // 꿀팁
                "추천할 만한 금융 상품이 있다면 알려주세요.", // 질문
                "가성비 좋은 보험 상품을 추천해드립니다.", // 꿀팁
                "돈을 꾸준히 모으기 위한 최고의 방법이 궁금합니다.", // 질문
                "불필요한 지출을 확인하고 줄이는 방법을 공유합니다.", // 꿀팁
                "실제 생활비를 줄이는 도전기를 공유해요.", // 꿀팁
                "대학생이 예산을 효과적으로 관리할 수 있는 방법을 알고 싶어요.", // 질문
                "초보자도 가능한 부동산 투자법이 있을까요?", // 질문
                "카드 할인 혜택을 놓치지 않는 방법을 알려드립니다.", // 꿀팁
                "이번 달 카드값이 너무 나왔는데, 해결 방법이 있을까요?", // 질문
                "30일 쇼핑 금지 챌린지에 도전해보세요!", // 꿀팁
                "세금 환급을 확실하게 받는 방법을 정리했습니다.", // 꿀팁
                "주식 투자 시 주의해야 할 점을 정리했습니다.", // 꿀팁
                "저축 목표를 쉽게 세우는 방법을 공유합니다.", // 질문
        };
        //given
        Board.Category[] categories = Board.Category.values();

        for (int i = 0; i < 40; i++) {
            Long userId = (long) ThreadLocalRandom.current().nextInt(4, 11); // 4~10 사이의 숫자
            String title = titles[ThreadLocalRandom.current().nextInt(titles.length)];
            String content = contents[ThreadLocalRandom.current().nextInt(contents.length)];
            Board.Category category = categories[ThreadLocalRandom.current().nextInt(categories.length)];

            BoardSaveDto built = BoardSaveDto.builder()
                    .userId(userId)
                    .title(title)
                    .content(content)
                    .category(category)
                    .build();

            boardService.saveBoard(built);
        }
        //when

        //then
    }
}