package com.easy2remember.poster_back.service;

import com.easy2remember.poster_back.dao.impl.*;
import com.easy2remember.poster_back.entity.impl.*;
import com.easy2remember.poster_back.enums.NotificationTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public int createNewPost(Post post, LocalDateTime dateTime, NotificationTypeEnum[] notificationType) {
        //------------------------------------------------------------------------------------------------------------------
        PostAnalyticsSnap postAnalyticsSnap = this.postAnalyticsService.createPostAnalyticsSnap();
        //------------------------------------------------------------------------------------------------------------------
        post.setPostAnalyticsSnap(postAnalyticsSnap);
        //------------------------------------------------------------------------------------------------------------------
        long postId = -1L;
        if ((postId = this.jdbcPostRepo.save(post)) == -1L) {
            throw new EmptyResultDataAccessException("Post can't be created", 1);
        }
        if (this.notificationService.sendPostToAllSubscribers(post, dateTime, notificationType) == 0) {
            throw new RecoverableDataAccessException("Post's notification cannot be send");
        }
        //------------------------------------------------------------------------------------------------------------------
        PostAnalyticsInfo postAnalyticsInfo = this.postAnalyticsService.createPostAnalyticsInfoByPostId(postId);
        //------------------------------------------------------------------------------------------------------------------
        return 1;
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    //FIXME implement get post with updating analytics and add implementation for checking whether user came from referral link
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public PostDetails getPostDetails(Long postId, String invitorUsername, String referralUsername) {

        Optional<PostDetails> respond = this.jdbcPostDetailsRepo.findById(postId);

        if (respond.isEmpty()) {
            return null;
        }
        //------------------------------------------------------------------------------------------------------------------
        Optional<Post> post = this.jdbcPostRepo.findById(respond.get().getPostId());

        if (post.isEmpty()) {
            throw new RuntimeException("IMPLEMENT AN EXCEPTION");
        }
        //------------------------------------------------------------------------------------------------------------------
        PostAnalyticsSnap pas = post.get().getPostAnalyticsSnap();
        Long userId = null;

        if (invitorUsername == null) {
            if (post.get().isExclusive()) {
                throw new RuntimeException("Data access exception");
            }

            userId = this.jdbcUserRepo.findIdByName(invitorUsername);
            if (userId == null) {
                throw new RuntimeException("No user Exicts");
            }

        } else {

            if (post.get().isExclusive() && !post.get().getUsersIdWithAccess().contains(userId)) {
                throw new RuntimeException("Data access exception");
            }

            pas.setViewedNum(pas.getNumOfSpreads() + 1);

            this.jdbcUserRepo.findByName(referralUsername).ifPresent(user -> {
                List<Long> viewersIdList = new ArrayList<>(pas.getViewersId());
                viewersIdList.add(user.getId());
                pas.setViewersId(viewersIdList);
            });

            //----------------------------------------------------------------------------------------------------------
            if (referralUsername != null) {
                pas.setViewedByReferralNum(pas.getViewedByReferralNum() + 1);
                this.jdbcUserRepo.findByName(referralUsername).ifPresent(user -> {
                    List<Long> refViewersIdList = new ArrayList<>(pas.getRefViewersId());
                    refViewersIdList.add(user.getId());
                    pas.setRefViewersId(refViewersIdList);
                });
            }
        }
        //------------------------------------------------------------------------------------------------------------------
        if (this.postAnalyticsService.updatePostAnalyticsSnap(pas, pas.getId()) != 1) {
            throw new EmptyResultDataAccessException("PostAnalyticsSnap can't be updated", 1);
        }
        //------------------------------------------------------------------------------------------------------------------
        post.get().setPostAnalyticsSnap(pas);

        if (this.jdbcPostRepo.updateById(post.get(), post.get().getId()) != 1) {
            throw new EmptyResultDataAccessException("Post can't be updated", 1);
        }
        //------------------------------------------------------------------------------------------------------------------
        return respond.get();
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    public Post[] getAllPosts() {
        return this.jdbcPostRepo.findAll();
    }

    public Post[] getAllPostsForToday() {
        return this.jdbcPostRepo.findAllPostsForToday();
    }

    public int updatePost(Post updatedPost, Long postId) {
        return this.jdbcPostRepo.updateById(updatedPost, postId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public int removePost(Long postId, String username) {

        User user = this.jdbcUserRepo.findByName(username).orElseGet(() -> null);
        if(user == null) {
            throw new EmptyResultDataAccessException("username doesn't exists", 1);
        }

        List<Long> userPostIdList = user.getPostIdList();
        if(userPostIdList == null) {
            throw new EmptyResultDataAccessException("user doesn't have any posts", 1);
        }

        if(!userPostIdList.contains(postId)) {
            throw new RuntimeException("Post id does not belong to the user");
        }

        userPostIdList = new ArrayList<>(userPostIdList);

        for(int i = 0; i < userPostIdList.size(); ++i) {
            if(Objects.equals(userPostIdList.get(i), postId)) {
                userPostIdList.remove(i);
                break;
            }
        }

        if(this.jdbcUserRepo.updateById(user, user.getId()) != 1) {
            throw new EmptyResultDataAccessException("user can't be updated", 1);
        }

        if(this.jdbcPostRepo.deleteById(postId) != 1) {
            throw new EmptyResultDataAccessException("post can't be deleted", 1);
        }


        return 1;
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


}
