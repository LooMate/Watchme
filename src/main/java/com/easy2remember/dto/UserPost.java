package com.easy2remember.dto;

import com.easy2remember.entity.impl.main.Post;
import com.easy2remember.entity.impl.details.PostDetails;

public record UserPost(Post post, PostDetails postDetails) {
}
