package com.easy2remember.dao.util;

import com.easy2remember.entity.impl.main.User;

public interface UserRepo extends UserAbstractRepo<User> {

    /**
     * saveReferralIdToInvitor saves "invited user's id" to invitor referrals list
     *
     * @param referralId invited user's id
     */
    int saveReferralIdToInvitor(Long referralId, Long invitorId);

    /**
     * saveSubscriberToPublisherById saves a user's id "subscriber" to the publisher by "id"
     */
    int saveSubscriberToPublisherById(Long subId, Long toPubId);

    /**
     * savePublisherIdToSubscriber save "publisher's id" to a user's "follows" list
     */
    int savePublisherIdToSubscriber(Long pubId, Long toSubId);

    Long findIdByName(String name);

    Long[] findPostsIdsByUserId(Long userId);

    User[] findAllPublisherSubscribersById(Long publisherId);

    /**
     * saveSubscriberToPublisherById removes a user's id "subscriber" from the publisher by "id"
     */
    int removeSubscriberFromPublisherById(Long subId, Long fromPubId);

    /**
     * savePublisherIdToSubscriber removes "publisher's id" from a user's "follows" list
     */
    int removePublisherIdFromSubscriber(Long pubId, Long fromSubId);
}
