package com.project.api.repository;

import com.project.api.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    public List<Notice> findByUserId(Long userId);

}
