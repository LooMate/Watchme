package com.easy2remember.poster_back.entity.util;

import java.time.LocalDateTime;

public abstract class AbstractEntity {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime lastChangedAt;

    protected AbstractEntity() {}

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
}
