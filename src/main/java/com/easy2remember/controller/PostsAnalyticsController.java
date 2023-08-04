package com.easy2remember.controller;


import com.easy2remember.service.PostAnalyticsService;
import com.easy2remember.dto.PostAnalyticsDetailsDto;
import com.easy2remember.entity.impl.details.PostAnalyticsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/analytics")
public class PostsAnalyticsController {

    private PostAnalyticsService postAnalyticsService;

    private Logger logger = LoggerFactory.getLogger(PostsAnalyticsController.class);

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public PostsAnalyticsController(PostAnalyticsService postAnalyticsService) {
        this.postAnalyticsService = postAnalyticsService;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    /**
     * pI - post id
     * uI - user id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("show")
    public PostAnalyticsDetailsDto getPostAnalyticsDetails(@RequestParam("pI") Long postId,
                                                                           @RequestParam("uI") Long userId) {
        logger.trace("Post analytics show endpoint was triggered for post {} by user {}", postId, userId);
        return this.postAnalyticsService.getPostAnalyticsDetailsByPostId(postId, userId);
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    /**
     * paI - postAnalyticsId
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/status/change")
    public String changeStateOfPostAnalytics(@RequestParam("paI") Long postAnalyticsId, @RequestParam("uI") Long userId,
                                             @RequestParam("change") String changedState) {
        logger.trace("Post analytics update/status/change was triggered for post analytics {} by user {} set to {}",
                postAnalyticsId, userId, changedState);
        this.postAnalyticsService.changeStateOfAnalyticsByPostId(postAnalyticsId, userId, changedState);
        return "State of Post's analytics were changed to " + changedState.toUpperCase();
    }
    //-----------------------------------------------------------------------------------------------------------------------
    /**
     * uI - userId
     * pI - postId
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/post_analytics_info")
    public String updatePostAnalyticsInfo(@RequestBody PostAnalyticsInfo postAnalyticsInfo,
                                          @RequestParam("pI") Long postId,
                                          @RequestParam("uI") Long userId) {
        logger.trace("Post analytics update/post_analytics_info endpoint was triggered for post {} by user {}",
                postId, userId);
        int status = this.postAnalyticsService.updatePostAnalyticsInfo(postAnalyticsInfo, postId, userId);
        return status == 1 ? "Post analytics was updated" : "Post analytics was NOT updated";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    /**
     * uI - userId
     * pI - postId
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("delete/post_analytics")
    public String deletePostAnalytics(@RequestParam("pI") Long postId, @RequestParam("uI") Long userId) {
        logger.trace("Post delete/post_analytics endpoint was triggered for post {} by user {}", postId, userId);
        this.postAnalyticsService.deletePostAnalyticsByPostId(postId, userId);
        return "Post analytics was deleted";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
