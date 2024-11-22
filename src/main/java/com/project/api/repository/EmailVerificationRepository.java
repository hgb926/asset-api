package com.project.api.repository;

import com.project.api.entity.EmailVerification;
import com.project.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByUser(User user);
}
