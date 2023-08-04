package com.easy2remember.entity.util;

import java.time.LocalDateTime;

public abstract class UserDetailsAbstractEntity extends UserAbstractEntity{
    public UserDetailsAbstractEntity() {
    }

    public UserDetailsAbstractEntity(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String timeZone) {
        super(id, createdAt, lastChangedAt, timeZone);
    }

}
