package com.bichofull.backend.service;

import com.bichofull.backend.model.AuditLog;
import com.bichofull.backend.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(String action, String userEmail, String description) {

        AuditLog log = AuditLog.builder()
                .action(action)
                .userEmail(userEmail)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }
}