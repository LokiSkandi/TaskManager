package com.example.TaskManager.service;

import com.example.TaskManager.event.AuditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaAuditService {
    @Value("${spring.kafka.topic.audit-events}")
    private String auditTopic;
    @Autowired
    private KafkaTemplate<String, AuditEvent> kafkaTemplate;

    public void sendAuditEvent (AuditEvent auditEvent) {
        String key = auditEvent.getEntityType() + ":" + auditEvent.getEntityId();
        kafkaTemplate.send(auditTopic, key, auditEvent);
    }
}
