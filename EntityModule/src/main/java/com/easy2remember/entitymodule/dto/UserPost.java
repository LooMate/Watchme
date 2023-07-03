package com.easy2remember.entitymodule.dto;

import com.easy2remember.entitymodule.entity.impl.Post;
import com.easy2remember.entitymodule.entity.impl.PostDetails;

public record UserPost(Post post, PostDetails postDetails) {
}
