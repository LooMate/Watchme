package com.easy2remember.controller;


import com.easy2remember.dto.PostDetailsDto;
import com.easy2remember.dto.PostDto;
import com.easy2remember.dto.UserPost;
import com.easy2remember.entity.impl.details.PostDetails;
import com.easy2remember.entity.impl.main.Post;
import com.easy2remember.exceptions.posts.PostsWereNotFoundException;
import com.easy2remember.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/post")
public class PostsController {

    private PostService postService;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public PostsController(PostService postService) {
        this.postService = postService;
    }

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    public String createPost(@RequestBody() UserPost userPost, @RequestParam String username,
                             @RequestParam("send_type") String[] sendNotificationTypes) {
        this.postService.createNewPost(userPost, username, sendNotificationTypes);
        return "Post " + userPost.post().getPostName() + " was successfully created";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * inv - invitor
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("more")
    public PostDetailsDto showPostDetails(@RequestParam("pI") Long postId,
                                          @RequestParam(value = "inv", required = false) String invitorUsername,
                                          @RequestParam(value = "view", required = false) String viewer) {
        return PostDetails.generateDto(this.postService.getPostDetails(postId, invitorUsername, viewer));
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("overview")
    public PostDto[] showAllPosts() {
        try {
            return Post.generateDtoArray(this.postService.getAllPosts());
        } catch (EmptyResultDataAccessException e) {
            throw new PostsWereNotFoundException("Posts were NOT found");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{username}/overview")
    public PostDto[] showAllPublishersPosts(@PathVariable("username") String username) {
        try {
            return Post.generateDtoArray(this.postService.getAllPublishersPosts(username));
        } catch (EmptyResultDataAccessException e) {
            throw new PostsWereNotFoundException("Posts were NOT found");
        }
    }

    @GetMapping("daily")
    @ResponseStatus(HttpStatus.OK)
    public PostDto[] showAllPostsForToday() {
        try {
            return Post.generateDtoArray(this.postService.getAllPostsForToday());
        } catch (EmptyResultDataAccessException e) {
            throw new PostsWereNotFoundException("Posts were NOT found");
        }
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * 1 post was updated
     * 0 post wasn't updated
     * own - post's owner
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update")
    public String updatePost(@RequestBody Post updatedPost, @RequestParam("ownr") String username) {
        if (this.postService.updatePost(updatedPost, username) == 1) {
            return "Post was successfully updated";
        }
        return "Post was NOT updated";
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * own - post's owner
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("delete")
    public String deletePost(@RequestParam("pI") Long postId, @RequestParam("ownr") String username) {
        this.postService.removePost(postId, username);
        return "Post was successfully deleted";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
