package com.easy2remember.entitymodule.entity.util;

import java.time.LocalDateTime;

/**
 * AbstractEntity class is a parent for all entities across whole project
 */
public abstract class AbstractEntity {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime lastChangedAt;

    protected AbstractEntity() {
    }

    protected AbstractEntity(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastChangedAt = lastChangedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastChangedAt() {
        return lastChangedAt;
    }

    public void setLastChangedAt(LocalDateTime lastChangedAt) {
        this.lastChangedAt = lastChangedAt;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", lastChangedAt=" + lastChangedAt +
                '}';
    }
}
