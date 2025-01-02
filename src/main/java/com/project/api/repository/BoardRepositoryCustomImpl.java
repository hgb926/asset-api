package com.project.api.repository;

import com.project.api.entity.Board;
import com.project.api.entity.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Page<Board> getSearchResult(Pageable pageable, String category, String keyword, String searchType) {
        QBoard board = QBoard.board; // QueryDSL용 엔티티 메타 모델

        // 동적 쿼리
        List<Board> results = factory.selectFrom(board)
                .where(
                        eqCategory(category),
                        searchByType(searchType, keyword)
                )
                .offset(pageable.getOffset()) // 시작 인덱스
                .limit(pageable.getPageSize()) // 페이지당 개수
                .orderBy(board.createdAt.desc()) // 최신순 정렬
                .fetch();

        // 전체 게시글 수 계산
        Long total = factory.select(board.count())
                .from(board)
                .where(
                        eqCategory(category),
                        searchByType(searchType, keyword)
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    // 카테고리 필터링
    private BooleanExpression eqCategory(String category) {
        return (category != null && !category.isEmpty())
                ? QBoard.board.category.eq(Board.Category.valueOf(category.toUpperCase()))
                : null;
    }

    // 검색 조건에 따른 필터링
    private BooleanExpression searchByType(String searchType, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        switch (searchType) {
            case "title":
                return QBoard.board.title.containsIgnoreCase(keyword);
            case "titleAndContent":
                return QBoard.board.title.containsIgnoreCase(keyword)
                        .or(QBoard.board.content.containsIgnoreCase(keyword));
            case "author":
                return QBoard.board.user.nickname.containsIgnoreCase(keyword);
            default:
                return null;
        }
    }
}