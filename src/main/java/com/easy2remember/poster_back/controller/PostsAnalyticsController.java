package com.easy2remember.poster_back.controller;


import com.easy2remember.poster_back.entity.impl.PostAnalyticsInfo;
import com.easy2remember.poster_back.service.PostAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsAnalyticsController {

    private PostAnalyticsService postAnalyticsService;

    @Autowired
    public PostsAnalyticsController(PostAnalyticsService postAnalyticsService) {
        this.postAnalyticsService = postAnalyticsService;
    }

//    ////////////////////////////
    public String buyAnalytics(String jwt) {
        return null;
    }
//    ///////////////////////////

    public PostAnalyticsInfo[] getPostAnalyticsDetails(Long postId, String jwt) {
        return null;
    }

    public String stopPostAnalytics(Long postAnalyticsId, String jwt) {
        return null;
    }

    public String updatePostAnalyticsFrequency(Long postAnalyticsId, int newFrequency, String jwt) {
        return null;
    }


}
