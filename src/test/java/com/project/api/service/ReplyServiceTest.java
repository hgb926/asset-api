package com.project.api.service;

import com.project.api.dto.request.ReplySaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


@SpringBootTest
//@Transactional
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    private final Random random = new Random();

    @Test
    @DisplayName("더미 댓글 데이터 삽입")
    void addDummyData() {
        // 댓글 내용 더미 데이터 (50개)
        String[] contents = {
                "정말 좋은 정보네요! 감사합니다.",
                "질문이 있습니다. 조금 더 설명해주실 수 있나요?",
                "저도 비슷한 경험이 있어요!",
                "공감합니다. 저도 그렇게 생각해요.",
                "더 자세한 팁이 있으면 알려주세요!",
                "좋은 아이디어네요. 시도해보겠습니다.",
                "이 글을 보고 많은 걸 배웠어요.",
                "실제로 해보신 건가요?",
                "저도 같은 고민이 있습니다.",
                "유용한 팁이에요. 공유해주셔서 감사합니다!",
                "정말 궁금했는데 해결됐어요.",
                "여기서 더 배울 점이 많네요.",
                "설명해주셔서 감사합니다!",
                "같은 문제로 고민 중이었는데 도움됐습니다.",
                "혹시 다른 방법도 있을까요?",
                "경험을 나눠주셔서 감사합니다.",
                "유익한 정보 감사드립니다.",
                "따라 해보고 싶어졌어요!",
                "더 많은 팁 기대할게요.",
                "궁금한 점이 해결되었습니다!",
                "이 팁은 정말 효과적이네요.",
                "아직 실천해보지 않았는데, 곧 해볼게요!",
                "같은 상황에 있었는데 도움됐습니다.",
                "설명이 너무 명확해서 좋았어요.",
                "이 글을 친구에게도 공유해야겠네요.",
                "경험이 묻어나는 글입니다.",
                "다른 팁도 공유해주시면 좋겠어요.",
                "읽는 내내 공감했습니다.",
                "작은 팁이지만 큰 도움이 됐어요.",
                "제가 겪었던 문제와 비슷하네요.",
                "정말 많은 도움이 됐습니다.",
                "이런 내용은 처음 봤어요. 신기하네요!",
                "구체적인 예시가 있어서 이해하기 쉬웠어요.",
                "더 많은 질문을 하고 싶어졌습니다.",
                "누군가에게 꼭 필요한 정보입니다.",
                "다시 한 번 감사드려요.",
                "경험에서 나온 말이라 신뢰가 갑니다.",
                "유익한 시간이었습니다.",
                "읽기만 해도 실천하고 싶어지네요.",
                "이런 팁을 더 많이 알고 싶어요.",
                "글이 너무 깔끔하고 명확하네요.",
                "제 친구도 이걸 알았으면 좋겠어요.",
                "항상 좋은 글 감사합니다.",
                "글쓴이의 경험이 느껴집니다.",
                "간단하면서도 핵심을 짚어주셨네요.",
                "이런 꿀팁은 언제나 환영입니다!",
                "몇 가지 따라 해봤는데 효과가 있네요.",
                "설명이 부족한 부분이 조금 있네요.",
                "앞으로도 자주 방문할게요.",
                "궁금한 부분은 추가로 질문드리겠습니다.",
                "실제 사례라 더 와닿네요.",
                "글쓴이의 노력이 보입니다.",
                "더 많은 정보 기대할게요!"
        };

        // 40개의 게시글(9~88 ID 범위)에 평균 10개씩 댓글 삽입
        for (int boardId = 300; boardId <= 409; boardId++) {
            // 각 게시글당 5~15개의 댓글을 무작위로 생성
            int replyCount = random.nextInt(11) + 5; // 5 ~ 15

            for (int i = 0; i < replyCount; i++) {
                Long userId = (long) (random.nextInt(7) + 4); // userId: 4 ~ 10
                String content = contents[random.nextInt(contents.length)]; // 무작위 내용 선택

                // DTO 생성
                ReplySaveDto replySaveDto = ReplySaveDto.builder()
                        .userId(userId)
                        .boardId((long) boardId)
                        .content(content)
                        .build();

                // 댓글 저장
                replyService.saveReply(replySaveDto);
            }
        }
    }
}