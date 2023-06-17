package com.easy2remember.poster_back.dao.util;

import com.easy2remember.poster_back.entity.impl.User;

public interface UserRepo extends UserAbstractRepo<User>{

    int saveReferralIdToInvitor(Long referralId, Long invitorId);

    String findReferralCodeFromUserById(Long fromUserId);

    int saveReferralCodeToUserById(Long userId, String referralCode);

    String[] findAllReferralCodes();

    int saveSubscriberToPublisherById(Long subId, Long toPubId);

    int removeSubscriberFromPublisherById(Long subId, Long fromPubId);

    int savePublisherIdToSubscriber(Long pubId, Long toSubId);

    int removePublisherIdFromSubscriber(Long pubId, Long fromSubId);

    Long findIdByName(String name);

    Long[] findPostIdByUserId(Long userId);

    Long[] findAllPublisherSubscribersId(Long publisherId);

    User[] findAllPublisherSubscribersById(Long publisherId);

    String[] findAllEmails();
}
