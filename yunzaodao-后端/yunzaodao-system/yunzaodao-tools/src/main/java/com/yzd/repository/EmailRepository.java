package com.yzd.repository;

import com.yzd.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmailRepository extends JpaRepository<EmailConfig,Long> {
}
