package com.easy2remember.entity.impl.details;

import com.easy2remember.dto.PostDetailsDto;
import com.easy2remember.entity.util.PostAbstractEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

public class PostDetails extends PostAbstractEntity {

    private String description;

    private byte[] image;

    private Long postId;

    public PostDetails() {
    }

    public PostDetails(String description, byte[] image) {
        this(null, null, null, description, image, null);
    }

    public PostDetails(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String description,
                       byte[] image, Long postId) {
        super(id, createdAt, lastChangedAt);
        this.description = description;
        this.image = image;
        this.postId = postId;
    }

    public static PostDetailsDto generateDto(PostDetails postDetails) {
        return new PostDetailsDto(postDetails.getCreatedAt(),
                postDetails.getLastChangedAt(),
                postDetails.getDescription(),
                postDetails.getImage()
        );
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

    @Override
    public String toString() {
        return "PostDetails{" +
                "description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", postId=" + postId +
                '}';
    }
}
