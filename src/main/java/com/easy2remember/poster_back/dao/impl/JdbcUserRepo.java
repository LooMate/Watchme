package com.easy2remember.poster_back.dao.impl;

import com.easy2remember.poster_back.dao.util.UserRepo;
import com.easy2remember.poster_back.entity.impl.User;
import com.easy2remember.poster_back.enums.InvitedFromType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

// FIXME: 6/12/2023
/*
 * FROM \"user\" WHERE id = ?
 * make it reusable
 * */

@Repository
public class JdbcUserRepo extends JdbcAbstractRepo<User> implements UserRepo {

    private final RowMapper<User> rowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("lastChangedAt").toLocalDateTime(),
                    rs.getString("timeZone"),
                    rs.getString("username"), rs.getString("email"),
                    rs.getString("usedReferralCode"), rs.getString("referralCode"),
                    List.of((Long) (rs.getArray("postIdList").getArray())),
                    List.of((Long) (rs.getArray("invitedUsersIdList").getArray())),
                    List.of((Long) (rs.getArray("subscribersIdList").getArray())),
                    List.of((Long) (rs.getArray("subscribedPublisherIdList").getArray())),
                    rs.getBoolean("isInvited"), rs.getObject("invitedFrom", InvitedFromType.class),
                    rs.getBoolean("isPremium")
            );


    public JdbcUserRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(User user) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO \"user\"" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, user.getId());
            ps.setTimestamp(2, Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(3, Timestamp.valueOf(user.getLastChangedAt()));
            ps.setString(4, user.getTimeZone());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getUsedReferralCode());
            ps.setString(8, user.getReferralCode());
            ps.setArray(9, con.createArrayOf("bigint", user.getPostIdList().toArray()));
            ps.setArray(10, con.createArrayOf("bigint", user.getInviteUsersIdList().toArray()));
            ps.setArray(11, con.createArrayOf("bigint", user.getSubscribersIdList().toArray()));
            ps.setArray(12, con.createArrayOf("bigint", user.getSubscribersIdList().toArray()));
            ps.setBoolean(13, user.isInvited());
            ps.setString(14, user.getInvitedFrom().toString());
            ps.setBoolean(15, user.isPremium());
            return ps;
        };

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, kh);         //orElseGet(() -> user.getId())
        return Optional.ofNullable(kh.getKey()).orElseGet(() -> -1).longValue();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"user\" WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"user\" WHERE name = ?",
                this.rowMapper, name));
    }

    @Override
    public User[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM \"user\"", this.rowMapper, null).toArray(User[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM \"user\" WHERE id = ?", id);
    }

    @Override
    public int updateById(User updated, Long id) {
        return this.jdbcTemplate.update("UPDATE \"user\" SET username = ?, email = ?, isPremium = ? WHERE id = ?",
                updated.getUsername(), updated.getEmail(), updated.isPremium());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public int saveReferralIdToInvitor(Long referralId, Long invitorId) {
        return jdbcTemplate.update("UPDATE \"user\" SET invitedUsersIdList = ARRAY_APPEND(invitedUsersIdList, ?) " +
                "WHERE id = ?", referralId, invitorId);
    }

    @Override
    public String findReferralCodeFromUserById(Long fromUserId) {
        return this.jdbcTemplate.queryForObject("SELECT referalCode FROM \"user\" WHERE id = ?",
                String.class, fromUserId);
    }

    @Override
    public int saveReferralCodeToUserById(Long userId, String referralCode) {
        return this.jdbcTemplate.update("UPDATE \"user\" SET referalCode = ? WHERE id = ?", referralCode, userId);
    }

    @Override
    public String[] findAllReferralCodes() {
        return this.jdbcTemplate.queryForObject("SELECT referalCode FROM \"user\"", String[].class, null);
    }

    @Override
    public int saveSubscriberToPublisherById(Long subId, Long toPubId) {
        return jdbcTemplate.update("UPDATE \"user\" SET subscribersIdList = ARRAY_APPEND(subscribersIdList, ?) " +
                        "WHERE id = ?",
                subId, toPubId);
    }

    @Override
    public int removeSubscriberFromPublisherById(Long subId, Long fromPubId) {
        return jdbcTemplate.update("UPDATE \"user\" SET subscribersIdList = ARRAY_REMOVE(subscribersIdList, ?) " +
                        "WHERE id = ?",
                subId, fromPubId);
    }

    @Override
    public int savePublisherIdToSubscriber(Long pubId, Long toSubId) {
        return jdbcTemplate.update("UPDATE \"user\" " +
                        "SET subscribedPublisherIdList = ARRAY_APPEND(subscribedPublisherIdList, ?) " +
                        "WHERE id = ?",
                pubId, toSubId);
    }

    @Override
    public int removePublisherIdFromSubscriber(Long pubId, Long fromSubId) {
        return jdbcTemplate.update("UPDATE \"user\" " +
                        "SET subscribedPublisherIdList = ARRAY_REMOVE(subscribedPublisherIdList, ?) " +
                        "WHERE id = ?",
                pubId, fromSubId);
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    @Override
    public Long findIdByName(String name) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT id FROM \"user\" WHERE username = ?", Long.class, name);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Long[] findPostIdByUserId(Long userId) {
        return this.jdbcTemplate.queryForObject("SELECT ARRAY_AGG(postIdList) FROM \"user\" WHERE id = ?",
                Long[].class, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long[] findAllPublisherSubscribersId(Long publisherId) {
        return jdbcTemplate.queryForObject("SELECT ARRAY_AGG(subscribersIdList) FROM \"user\" WHERE id = ?",
                Long[].class, publisherId);
    }

    @Transactional(readOnly = true)
    @Override
    public User[] findAllPublisherSubscribersById(Long publisherId) {
        return jdbcTemplate.query("SELECT * FROM \"user\" WHERE id IN " +
                        "(SELECT UNNEST(subscribersIdList) FROM \"user\" WHERE id = ?)",
                this.rowMapper, publisherId).toArray(User[]::new);
    }

    @Transactional(readOnly = true)
    @Override
    public String[] findAllEmails() {
        return this.jdbcTemplate.queryForObject("SELECT email FROM \"user\"", String[].class, null);
    }

}
