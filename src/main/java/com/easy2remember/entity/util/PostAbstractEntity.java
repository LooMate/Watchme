package com.easy2remember.entity.util;

import java.time.LocalDateTime;

public abstract class PostAbstractEntity extends AbstractEntity {


    public PostAbstractEntity() {
    }

    public PostAbstractEntity(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt) {
        super(id, createdAt, lastChangedAt);
    }

}
