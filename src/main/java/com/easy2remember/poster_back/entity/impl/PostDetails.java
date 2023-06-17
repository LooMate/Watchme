package com.easy2remember.poster_back.entity.impl;

import com.easy2remember.poster_back.entity.util.PostAbstractEntity;

import java.time.LocalDateTime;

public class PostDetails extends PostAbstractEntity {

    private String description;

    private byte[] image;

    private Long postId;

    public PostDetails() {
    }

    public PostDetails(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String description,
                       byte[] image, Long postId) {
        super(id, createdAt,lastChangedAt);
        this.description = description;
        this.image = image;
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
