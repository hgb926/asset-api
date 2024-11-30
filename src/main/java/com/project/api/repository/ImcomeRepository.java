package com.project.api.repository;

import com.project.api.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImcomeRepository extends JpaRepository<Income, Long> {
}
