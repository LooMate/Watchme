package com.easy2remember.exceptions.posts;

import org.springframework.dao.DataAccessException;

public class PostsWereNotFoundException extends DataAccessException {
    public PostsWereNotFoundException(String msg) {
        super(msg);
    }
}
