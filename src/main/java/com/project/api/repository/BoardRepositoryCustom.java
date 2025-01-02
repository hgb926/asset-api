package com.project.api.repository;

import com.project.api.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> getSearchResult(Pageable pageable, String category, String keyword, String searchType);
}
