package com.project.api.repository;

import com.project.api.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 페이징된 게시글 목록을 반환
    Page<Board> findAll(Pageable pageable);
}