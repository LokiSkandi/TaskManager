package com.example.TaskManager.event;


import java.time.LocalDateTime;

public class AuditEvent {

    private String serviceName;
    private String action;
    private String entityType;
    private Long entityId;
    private LocalDateTime timestamp;

    public AuditEvent(String serviceName, String action, String entityType, Long entityId, LocalDateTime timestamp) {
        this.serviceName = serviceName;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.timestamp = timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getAction() {
        return action;
    }

    public String getEntityType() {
        return entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
