package com.easy2remember.controllerModule.service;

import com.easy2remember.controllerModule.exceptions.posts.*;
import com.easy2remember.controllerModule.exceptions.users.SpecifiedPostDoesNotBelongToUserException;
import com.easy2remember.daomodule.dao.impl.JdbcPostAnalyticsInfoRepo;
import com.easy2remember.daomodule.dao.impl.JdbcPostAnalyticsSnapRepo;
import com.easy2remember.daomodule.dao.impl.JdbcPostRepo;
import com.easy2remember.daomodule.dao.impl.JdbcUserRepo;
import com.easy2remember.entitymodule.dto.PostAnalyticsDetailsDto;
import com.easy2remember.entitymodule.entity.impl.PostAnalyticsInfo;
import com.easy2remember.entitymodule.entity.impl.PostAnalyticsSnap;
import com.easy2remember.entitymodule.enums.StateOfPostAnalyticsEnum;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Transactional(propagation = Propagation.MANDATORY)
    public PostAnalyticsSnap createPostAnalyticsSnap() {
        PostAnalyticsSnap postAnalyticsSnap = new PostAnalyticsSnap(null, LocalDateTime.now(), LocalDateTime.now(),
                0L, 0L, 0L,
                new ArrayList<>(), new ArrayList<>(), 0L);

        // save post analytics snap and receive its id
        long pasId = this.jdbcPostAnalyticsSnapRepo.save(postAnalyticsSnap);
        if (pasId == -1L) {
            throw new PostAnalyticsSnapWasNotSavedException("Post analytics snap was NOW saved", 1);
        }
        postAnalyticsSnap.setId(pasId);
        return postAnalyticsSnap;
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.MANDATORY)
    public PostAnalyticsInfo createPostAnalyticsInfoByPostId(Long postSnapId) {
        PostAnalyticsInfo postAnalyticsInfo = new PostAnalyticsInfo(null, LocalDateTime.now(), LocalDateTime.now(),
                true, this.ANALYTICS_FREQUENCY_IN_HOUR, List.of(postSnapId));

        var paiId = this.jdbcPostAnalyticsInfoRepo.save(postAnalyticsInfo);
        if (paiId == -1L) {
            throw new PostAnalyticsInfoWasNotSavedException("Post analytics info was NOT saved", 1);
        }
        postAnalyticsInfo.setId(paiId);
        return postAnalyticsInfo;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int updatePostAnalyticsInfo(PostAnalyticsInfo updatedPai, Long postId, Long userId) {
        Long paiId = this.verifyAndGetPAIByPostId(postId, userId).getId();
        return this.jdbcPostAnalyticsInfoRepo.updateById(updatedPai, paiId);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int updatePostAnalyticsSnap(PostAnalyticsSnap updatedPas, Long pasId) {
        return this.jdbcPostAnalyticsSnapRepo.updateById(updatedPas, pasId);
    }
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PostAnalyticsInfo getPostAnalyticsInfoByPostId(Long postId) {
        Long postAnalyticsInfoId = this.jdbcPostAnalyticsInfoRepo.findPostAnalyticsInfoIdByPostId(postId);
        if (postAnalyticsInfoId == null) {
            throw new NoCuchPostANalyticsInfoForSpecifiedPostException("No such post analytics info's id For Specified Post", 1);
        }
        return this.jdbcPostAnalyticsInfoRepo.findById(postAnalyticsInfoId)
                .orElseThrow(() -> new PostAnalyticsInfoWasNotFoundException("Post analytics info wan Not Found", 1));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public PostAnalyticsDetailsDto getPostAnalyticsDetailsByPostId(Long postId, Long userId) {

        PostAnalyticsInfo pai = this.verifyAndGetPAIByPostId(postId, userId);
        PostAnalyticsSnap[] pass = this.jdbcPostAnalyticsSnapRepo.findAllPostAnalyticsSnapsByPostId(pai.getPostAnalyticsSnapsIdList());

        return new PostAnalyticsDetailsDto(pai, PostAnalyticsSnap.generateDtoArray(pass));
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void changeStateOfAnalyticsByPostId(Long postId, Long userId, String changedState) {

        //-------------------verify that post's id belongs to user by user id-----------------------------------------------
        PostAnalyticsInfo postAnalyticsInfo = verifyAndGetPAIByPostId(postId, userId);

        if (changedState == null || changedState.isBlank()) {
            throw new PostAnalyticsStateWasNotSpecifiedException("Post Analytics State was NOT provided");
        }
        //------------------------------------------------------------------------------------------------------------------
        StateOfPostAnalyticsEnum state = null;
        try {
            state = StateOfPostAnalyticsEnum.valueOf(changedState);
        } catch (IllegalArgumentException e) {
            throw new PostAnalyticsUnresolvableStateException("Post Analytics State Is WRONG");
        }
        postAnalyticsInfo.setActive(state.getStateOfAnalytics());
        //------------------------------------------------------------------------------------------------------------------
        if (this.jdbcPostAnalyticsInfoRepo.updateById(postAnalyticsInfo, postAnalyticsInfo.getId()) != 1) {
            throw new PostAnalyticsInfoWasNotSavedException("postAnalyticsInfo's status was NOT updated", 1);
        }
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deletePostAnalyticsByPostId(Long postId, Long userId) {

        //verify that post's id belongs to user by user id
        PostAnalyticsInfo postAnalyticsInfo = verifyAndGetPAIByPostId(postId, userId);

        if (this.jdbcPostAnalyticsInfoRepo.deleteById(postAnalyticsInfo.getId()) != 1) {
            throw new PostAnalyticsInfoWasNotDeletedException("postAnalyticsInfo was NOT deleted", 1);
        }
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Helper method which verifies whether postId belongs to the user
    public PostAnalyticsInfo verifyAndGetPAIByPostId(Long postId, Long userId) {

        //find post ids and check whether post's id belongs to the user
        List<Long> postIds = List.of(this.jdbcUserRepo.findPostsIdsByUserId(userId));
        if (!postIds.contains(postId)) {
            throw new SpecifiedPostDoesNotBelongToUserException("Not such post belongs to user");
        }
        return this.getPostAnalyticsInfoByPostId(postId);
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
