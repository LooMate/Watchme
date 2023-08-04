package com.easy2remember.service;

import com.easy2remember.exceptions.users.*;
import com.easy2remember.exceptions.posts.*;
import com.easy2remember.dao.impl.JdbcPostDetailsRepo;
import com.easy2remember.dao.impl.JdbcPostRepo;
import com.easy2remember.dao.impl.JdbcUserRepo;
import com.easy2remember.dto.UserPost;
import com.easy2remember.entity.impl.main.Post;
import com.easy2remember.entity.impl.details.PostAnalyticsSnap;
import com.easy2remember.entity.impl.details.PostDetails;
import com.easy2remember.entity.impl.main.User;
import com.easy2remember.enums.NotificationTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PostService {

    private JdbcUserRepo jdbcUserRepo;

    private JdbcPostRepo jdbcPostRepo;
    private JdbcPostDetailsRepo jdbcPostDetailsRepo;


    private PostAnalyticsService postAnalyticsService;
    private NotificationService notificationService;


    @Autowired
    public PostService(JdbcUserRepo jdbcUserRepo, JdbcPostRepo jdbcPostRepo, JdbcPostDetailsRepo jdbcPostDetailsRepo,
                       PostAnalyticsService postAnalyticsService, NotificationService notificationService) {
        this.jdbcUserRepo = jdbcUserRepo;
        this.jdbcPostRepo = jdbcPostRepo;
        this.jdbcPostDetailsRepo = jdbcPostDetailsRepo;

        this.postAnalyticsService = postAnalyticsService;
        this.notificationService = notificationService;
    }

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int createNewPost(UserPost userPost, String username, String[] stringNotificationTypes) {

        var post = userPost.post();

        //------Convert string notification type array into ENUM java type
        var notificationTypeEnums = new NotificationTypeEnum[stringNotificationTypes.length];
        try {
            for (int i = 0; i < stringNotificationTypes.length; i++) {
                notificationTypeEnums[i] = NotificationTypeEnum.valueOf(stringNotificationTypes[i].toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            throw new PostNotificationTypeException("Type: " + Arrays.toString(stringNotificationTypes) +
                    " do NOT exist");
        }

        var postAnalyticsSnap = this.postAnalyticsService.createPostAnalyticsSnap();
        post.setPostAnalyticsSnap(postAnalyticsSnap);

        //-----Create post analytics for tracking info about frequency and activity status-------------------------------------
        var postAnalyticsInfo = this.postAnalyticsService.createPostAnalyticsInfoByPostId(postAnalyticsSnap.getId());
        post.setPostAnalyticsInfoId(postAnalyticsInfo.getId());

        Long postId = this.jdbcPostRepo.save(post);
        if (postId == -1L) {
            throw new PostWasNotSavedException("Post " + post.getPostName() + "was NOT saved", 1);
        }
        //-----Create post details----------------------------------------------------------------------------------------------
        userPost.postDetails().setPostId(postId);
        if (this.jdbcPostDetailsRepo.save(userPost.postDetails()) != 1) {
            throw new PostDetailsWasNotSavedException("Post's details were NOT saved", 1);
        }

        //-----Save userPost id to user-----------------------------------------------------------------------------------------
        User user = this.jdbcUserRepo.findByUsername(username)
                .orElseThrow(() -> new UserWasNotFoundException("User does not exist", 1));

        var userPostIdList = new ArrayList<>(user.getPostsIdsList());
        userPostIdList.add(postId);
        user.setPostsIdsList(userPostIdList);

        if (this.jdbcUserRepo.updateById(user, user.getId()) != 1) {
            throw new UserWasNotSavedException("User was NOT updated", 1);
        }

        //-----Sends notification about new userPost to all subscribers---------------------------------------------------------
        if (this.notificationService.sendPostToAllSubscribers(post, LocalDateTime.now(), notificationTypeEnums) != 1) {
            throw new PostNotificationCouldNotBeSentException("Post notification was NOT sent");
        }
        return 1;
    }

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public PostDetails getPostDetails(Long postId, String invitorUsername, String viewer) {

        PostDetails postDetails = this.jdbcPostDetailsRepo.findByPostId(postId)
                .orElseThrow(() -> new PostDetailsWasNotFoundException("Post details is empty", 1));

        //--------------Check access for viewer-------------------------------------------------------------------------
        Post post = this.jdbcPostRepo.findById(postDetails.getPostId())
                .orElseThrow(() -> new PostWasNotFoundException("Post is empty", 1));

        Long userId = null;
        if (viewer != null && !viewer.isBlank()) {
            try {
                userId = this.jdbcUserRepo.findIdByName(viewer);
            } catch (EmptyResultDataAccessException e) {
                throw new UserWasNotFoundException("User does NOT exist", 1);
            }
        } else {
            viewer = null;
        }
        if (post.isExclusive()) {
            // I check whether viewer name is null, if it is then it is not allowed to see this post,
            // then I check whether this viewer is in the post's access list, if it is not present there
            // then it is not allowed to see this post
            if (viewer == null || post.getUsersIdWithAccess().contains(userId)) {
                throw new NoAccessToPostDetailsException("User does not have access to the resource");
            }
        }
        //------Record post's analytics if post analytics is active for this post-----------------------------------
        if (this.postAnalyticsService.getPostAnalyticsInfoByPostId(postId).isActive()) {
            this.recordAnalyticsForPost(postDetails, invitorUsername, viewer);
        }

        return postDetails;
    }

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.REPEATABLE_READ)
    public void recordAnalyticsForPost(PostDetails postDetails, String invitorUsername, String viewer) {

        Post post = this.jdbcPostRepo.findById(postDetails.getPostId())
                .orElseThrow(() -> new PostWasNotFoundException("Post was NOT found", 1));

        //--------------Record analytics for a post---------------------------------------------------------------------------------
        PostAnalyticsSnap pas = post.getPostAnalyticsSnap();

        // add viewer's id to post's analytics
        // if it is not provided then user is not logged in and list is not changed
        if (viewer != null && !viewer.isBlank()) {
            this.jdbcUserRepo.findByUsername(viewer).ifPresent(user -> {
                var viewersIdList = new ArrayList<>(pas.getViewersId());
                //If user already has seen this post it won't count that view
                if (viewersIdList.contains(user.getId())) {
                    return;
                }
                viewersIdList.add(user.getId());
                pas.setViewersId(viewersIdList);

                // add referral viewer's id to post's analytics if exists
                if (invitorUsername != null && !invitorUsername.isBlank()) {
                    var referralViewersIdList = new ArrayList<>(pas.getRefViewersId());
                    viewersIdList.add(user.getId());
                    pas.setRefViewersId(referralViewersIdList);
                }
                // increment total num of viewers
                pas.setViewedNum(pas.getViewedNum() + 1);
                pas.setViewedByReferralNum(pas.getViewedByReferralNum() + 1);
            });
            //-----Save changes to the database---------------------------------------------------------------------------------
            if (this.postAnalyticsService.updatePostAnalyticsSnap(pas, pas.getId()) != 1) {
                throw new PostAnalyticsSnapWasNotSavedException("Post Analytics Snap was NOT saved", 1);
            }
            post.setPostAnalyticsSnap(pas);
        }


        if (this.jdbcPostRepo.updateById(post, post.getId()) != 1) {
            throw new PostWasNotSavedException("Post was Not saved", 1);
        }
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    public Post[] getAllPosts() {
        return this.jdbcPostRepo.findAll();
    }

    public Post[] getAllPublishersPosts(String username) {
        return this.jdbcPostRepo.findAllByPublisherId(this.jdbcUserRepo.findIdByName(username));
    }

    public Post[] getAllPostsForToday() {
        return this.jdbcPostRepo.findAllPostsForToday();
    }

    public int updatePost(Post updatedPost, String username) {
        // check whether user have this post---------------------------------------------------------------------------------
        verifyPostOwnership(updatedPost.getId(), username);
        //-------------------------------------------------------------------------------------------------------------------
        updatedPost.setLastChangedAt(LocalDateTime.now());
        try {
            return this.jdbcPostRepo.updateById(updatedPost, updatedPost.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new PostWasNotUpdatedException("Post was NOT updated", 1);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int removePost(Long postId, String username) {
        // check whether user have this post---------------------------------------------------------------------------------
        User user = verifyPostOwnership(postId, username);
        //-------------------------------------------------------------------------------------------------------------------

        List<Long> userPostIdList = new ArrayList<>(user.getPostsIdsList());

        userPostIdList.remove(postId);
        user.setPostsIdsList(userPostIdList);

        if (this.jdbcUserRepo.updateById(user, user.getId()) != 1) {
            throw new UserWasNotSavedException("user can't be updated", 1);
        }

        if (this.jdbcPostRepo.deleteById(postId) != 1) {
            throw new PostWasNotSavedException("post can't be deleted", 1);
        }
        return 1;
    }

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public User verifyPostOwnership(Long postId, String username) {
        User user = this.jdbcUserRepo.findByUsername(username).orElseGet(() -> null);
        if (user == null) {
            throw new UserWasNotFoundException("Username does NOT exist", 1);
        }

        List<Long> userPostIdList = user.getPostsIdsList();
        if (userPostIdList == null) {
            throw new UserDoNotHaveAnyPostsException("User does NOT have any post", 1);
        }
        if (!userPostIdList.contains(postId)) {
            throw new SpecifiedPostDoesNotBelongToUserException("Post id does not belong to the user");
        }
        return user;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
