package com.easy2remember.controllerModule.controller;


import com.easy2remember.controllerModule.service.PostAnalyticsService;
import com.easy2remember.entitymodule.dto.PostAnalyticsDetailsDto;
import com.easy2remember.entitymodule.entity.impl.PostAnalyticsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/analytics")
public class PostsAnalyticsController {

    private PostAnalyticsService postAnalyticsService;

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
    @GetMapping("show")
    public ResponseEntity<PostAnalyticsDetailsDto> getPostAnalyticsDetails(@RequestParam("pI") Long postId,
                                                                           @RequestParam("uI") Long userId) {
        return ResponseEntity.ok(this.postAnalyticsService.getPostAnalyticsDetailsByPostId(postId, userId));
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    /**
     * paI - postAnalyticsId
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/status/change")
    public String changeStateOfPostAnalytics(@RequestParam("paI") Long postAnalyticsId, @RequestParam("uI") Long userId,
                                             @RequestParam("change") String changedState) {

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
        this.postAnalyticsService.deletePostAnalyticsByPostId(postId, userId);
        return "Post analytics was deleted";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
