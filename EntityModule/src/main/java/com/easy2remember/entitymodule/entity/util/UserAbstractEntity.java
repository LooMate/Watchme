package com.easy2remember.entitymodule.entity.util;

import java.time.LocalDateTime;

public abstract class UserAbstractEntity extends AbstractEntity{

    private String timeZone;

    public UserAbstractEntity() {
    }

    public UserAbstractEntity(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String timeZone) {
        super(id, createdAt, lastChangedAt);
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return
                super.toString() +"\nUserAbstractEntity{" +
                "timeZone='" + timeZone + '\'' +
                '}';
    }
}
