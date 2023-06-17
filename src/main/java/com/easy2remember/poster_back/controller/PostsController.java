package com.easy2remember.poster_back.controller;


import com.easy2remember.poster_back.entity.impl.Post;
import com.easy2remember.poster_back.entity.impl.PostDetails;
import com.easy2remember.poster_back.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class PostsController {

    private PostService postService;

    public PostsController() {
    }

    @Autowired
    public PostsController(PostService postService) {
        this.postService = postService;
    }


    public String createPost(Post post, LocalDateTime date, Object timeZone, String sendType) {
        return "";
    }

    public PostDetails showPostDetails(Long postId, String invitorUsername, String referralUsername) {
        return null;
    }

    public Post[] showAllPosts() {
        return null;
    }

    public Post[] showAllPostsForToday() {
        return null;
    }

    public String updatePost(Post updatedPost, Long postId) {
        return "";
    }

    public String deletePost(Long postId) {
        return "";
    }



}
