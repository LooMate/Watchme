package com.easy2remember.poster_back.service;

import com.easy2remember.poster_back.dao.impl.JdbcPostAnalyticsInfoRepo;
import com.easy2remember.poster_back.dao.impl.JdbcPostAnalyticsSnapRepo;
import com.easy2remember.poster_back.dao.impl.JdbcPostRepo;
import com.easy2remember.poster_back.dao.impl.JdbcUserRepo;
import com.easy2remember.poster_back.entity.impl.Post;
import com.easy2remember.poster_back.entity.impl.PostAnalyticsInfo;
import com.easy2remember.poster_back.entity.impl.PostAnalyticsSnap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostAnalyticsService {

    private JdbcPostRepo jdbcPostRepo;
    private JdbcUserRepo jdbcUserRepo;

    private JdbcPostAnalyticsInfoRepo jdbcPostAnalyticsInfoRepo;
    private JdbcPostAnalyticsSnapRepo jdbcPostAnalyticsSnapRepo;


    private final int ANALYTICS_FREQUENCY_IN_HOUR;

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @Autowired
    public PostAnalyticsService(JdbcPostAnalyticsInfoRepo jdbcPostAnalyticsInfoRepo,
                                JdbcPostAnalyticsSnapRepo jdbcPostAnalyticsSnapRepo, JdbcPostRepo jdbcPostRepo, JdbcUserRepo jdbcUserRepo) {
        this.jdbcPostAnalyticsSnapRepo = jdbcPostAnalyticsSnapRepo;
        this.jdbcPostAnalyticsInfoRepo = jdbcPostAnalyticsInfoRepo;

        this.jdbcPostRepo = jdbcPostRepo;
        this.jdbcUserRepo = jdbcUserRepo;

        ANALYTICS_FREQUENCY_IN_HOUR = 2;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public PostAnalyticsInfo createPostAnalyticsInfoByPostId(Long postId) {
        PostAnalyticsInfo postAnalyticsInfo = new PostAnalyticsInfo(null, LocalDateTime.now(), LocalDateTime.now(),
                true, this.ANALYTICS_FREQUENCY_IN_HOUR, postId);

        if (this.jdbcPostAnalyticsInfoRepo.save(postAnalyticsInfo) == -1) {
            throw new EmptyResultDataAccessException("PostAnalyticsInfo can't be created", 1);
        }

        return postAnalyticsInfo;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private PostAnalyticsInfo getPostAnalyticsInfoByPostId(Long postId) {
    return null;
}
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @Transactional(propagation = Propagation.MANDATORY)
    public PostAnalyticsSnap createPostAnalyticsSnap() {
        PostAnalyticsSnap postAnalyticsSnap = new PostAnalyticsSnap(null, LocalDateTime.now(), LocalDateTime.now(),
                0L, 0L, 0L,
                new ArrayList<>(), new ArrayList<>(), 0L);

        long pasId = this.jdbcPostAnalyticsSnapRepo.save(postAnalyticsSnap);
        if (pasId == -1) {
            throw new EmptyResultDataAccessException("postAnalyticsSnap can't be created", 1);
        }
        postAnalyticsSnap.setId(pasId);
        return postAnalyticsSnap;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private PostAnalyticsSnap[] getAllPostAnalyticsSnapsByPostId(Long postId) {
    return null;
}
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.MANDATORY)
    public int updatePostAnalyticsInfo(PostAnalyticsInfo updatedPai, Long paiId) {
        return this.jdbcPostAnalyticsInfoRepo.updateById(updatedPai, paiId);
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.MANDATORY)
    public int updatePostAnalyticsSnap(PostAnalyticsSnap updatedPas, Long pasId) {
        return this.jdbcPostAnalyticsSnapRepo.updateById(updatedPas, pasId);
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.MANDATORY)
    public int changePostAnalyticsSnapFrequencyByPostId(Long postId, Long userId, int newFrequency) {

        int response = -1;

        //verify that post's id belongs to user by user id
        PostAnalyticsInfo postAnalyticsInfo = verifyAndGetPAI(postId, userId);

        postAnalyticsInfo.setAnalyticsFrequencyInHour(newFrequency);

        if((response = this.jdbcPostAnalyticsInfoRepo.updateById(postAnalyticsInfo, postAnalyticsInfo.getId())) != 1) {
            throw new EmptyResultDataAccessException("postAnalyticsInfo's frequency can't be updated", 1);
        }

        return response;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public int disablePostAnalyticsByPostId(Long postId, Long userId) {
        int response = -1;

        //verify that post's id belongs to user by user id
        PostAnalyticsInfo postAnalyticsInfo = verifyAndGetPAI(postId, userId);

        postAnalyticsInfo.setActive(false);

        if((response = this.jdbcPostAnalyticsInfoRepo.updateById(postAnalyticsInfo, postAnalyticsInfo.getId())) != 1) {
            throw new EmptyResultDataAccessException("postAnalyticsInfo's status can't be updated", 1);
        }

        return response;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public int deletePostAnalyticsByPostId(Long postId, Long userId) {
        int response = -1;

        //verify that post's id belongs to user by user id
        PostAnalyticsInfo postAnalyticsInfo = verifyAndGetPAI(postId, userId);

        if((response = this.jdbcPostAnalyticsInfoRepo.deleteById(postAnalyticsInfo.getId())) != 1) {
            throw new EmptyResultDataAccessException("postAnalyticsInfo can't be deleted", 1);
        }

        return response;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private int scheduleAnalyticsSnap(Long postId, Long userId){
        return 1;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private boolean isUserPremium(Long userId) {
        return false;
    }

    //verifies whether postId is belonging to the user
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.MANDATORY)
    public PostAnalyticsInfo verifyAndGetPAI(Long postId, Long userId) {

        List<Long> postIds = List.of(this.jdbcUserRepo.findPostIdByUserId(userId));
        if(!postIds.contains(postId)) {
            throw new RuntimeException();
        }

        Long analyticsId = this.jdbcPostRepo.findById(postId).orElseGet(Post::new).getPostAnalyticsInfoId();
        if(analyticsId == null) {
            throw new RuntimeException();
        }

        PostAnalyticsInfo postAnalyticsInfo = this.jdbcPostAnalyticsInfoRepo.findById(analyticsId).orElseGet(() -> null);
        if(postAnalyticsInfo == null) {
            throw new RuntimeException();
        }

        return postAnalyticsInfo;
    }


}
