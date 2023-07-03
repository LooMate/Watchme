package com.easy2remember.daomodule.dao.impl;

import com.easy2remember.daomodule.dao.util.UserRepo;
import com.easy2remember.entitymodule.entity.impl.User;
import com.easy2remember.entitymodule.enums.InvitedFromType;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepo extends JdbcAbstractRepo<User> implements UserRepo {

    private final RowMapper<User> rowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("lastChangedAt").toLocalDateTime(),
                    rs.getString("timeZone"),
                    rs.getString("username"), rs.getString("email"),
                    rs.getString("usedReferralCode"), rs.getString("referralCode"),
                    List.of((Long[]) rs.getArray("postsIdsList").getArray()),
                    List.of((Long[]) rs.getArray("invitedUsersIdsList").getArray()),
                    List.of((Long[]) rs.getArray("subscribersIdsList").getArray()),
                    List.of((Long[]) rs.getArray("subscribedPublishersIdsList").getArray()),
                    rs.getBoolean("isInvited"),
                    InvitedFromType.valueOf(rs.getString("invitedFrom").toUpperCase()),
                    rs.getBoolean("isPremium")
            );


    public JdbcUserRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(User user) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO \"user\" (createdAt,lastChangedAt," +
                    "timeZone, username, email, usedReferralCode, referralCode, postsIdsList, invitedUsersIdsList," +
                    "subscribersIdsList, subscribedPublishersIdsList, isInvited, invitedFrom, isPremium) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, user.getTimeZone());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getUsedReferralCode());
            ps.setString(7, user.getReferralCode());
            ps.setArray(8, con.createArrayOf("bigint", new Long[0]));
            ps.setArray(9, con.createArrayOf("bigint", new Long[0]));
            ps.setArray(10, con.createArrayOf("bigint", new Long[0]));
            ps.setArray(11, con.createArrayOf("bigint", new Long[0]));
            ps.setBoolean(12, user.isInvited());
            ps.setString(13, user.getInvitedFrom().toString());
            ps.setBoolean(14, user.isPremium());
            return ps;
        };

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, kh);
        return Optional.ofNullable((Long) kh.getKeyList().get(0).get("id")).orElseGet(() -> -1L);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"user\" WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public Optional<User> findByUsername(String name) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"user\" WHERE username = ?",
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
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("UPDATE \"user\" SET lastChangedAt = ?, username = ?," +
                    " email = ?, postsIdsList = ?, isPremium = ? WHERE id = ?");
            ps.setTimestamp(1, Timestamp.valueOf(updated.getLastChangedAt()));
            ps.setString(2, updated.getUsername());
            ps.setString(3, updated.getEmail());
            ps.setArray(4, con.createArrayOf("bigint", updated.getPostsIdsList().toArray()));
            ps.setBoolean(5, updated.isPremium());
            ps.setLong(6, id);
            return ps;
        };

        return this.jdbcTemplate.update(psc);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public int saveReferralIdToInvitor(Long referralId, Long invitorId) {
        return jdbcTemplate.update("UPDATE \"user\" SET invitedUsersIdsList = ARRAY_APPEND(invitedUsersIdsList, ?) " +
                "WHERE id = ?", referralId, invitorId);
    }

    @Override
    public int saveSubscriberToPublisherById(Long subId, Long toPubId) {
        return jdbcTemplate.update("UPDATE \"user\" SET subscribersIdsList = ARRAY_APPEND(subscribersIdsList, ?) " +
                        "WHERE id = ?",
                subId, toPubId);
    }

    @Override
    public int savePublisherIdToSubscriber(Long pubId, Long toSubId) {
        return jdbcTemplate.update("UPDATE \"user\" " +
                        "SET subscribedPublishersIdsList = ARRAY_APPEND(subscribedPublishersIdsList, ?) " +
                        "WHERE id = ?",
                pubId, toSubId);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Long findIdByName(String name) {
        return this.jdbcTemplate.queryForObject("SELECT id FROM \"user\" WHERE username = ?", Long.class, name);
    }

    @Transactional(readOnly = true)
    @Override
    public Long[] findPostsIdsByUserId(Long userId) {
        return this.jdbcTemplate.queryForObject("SELECT postsIdsList FROM \"user\" WHERE id = ?",
                (rs, rowNum) -> (Long[]) (rs.getArray("postsIdsList")
                        .getArray()), userId);
    }

    @Transactional(readOnly = true)
    @Override
    public User[] findAllPublisherSubscribersById(Long publisherId) {
        return jdbcTemplate.query("SELECT * FROM \"user\" WHERE id IN (SELECT UNNEST(subscribersIdsList) " +
                        "FROM \"user\" WHERE id = ?)",
                this.rowMapper, publisherId).toArray(User[]::new);
    }

    @Override
    public int removeSubscriberFromPublisherById(Long subId, Long fromPubId) {
        return jdbcTemplate.update("UPDATE \"user\" SET subscribersIdsList = ARRAY_REMOVE(subscribersIdsList, ?) " +
                        "WHERE id = ?",
                subId, fromPubId);
    }

    @Override
    public int removePublisherIdFromSubscriber(Long pubId, Long fromSubId) {
        return jdbcTemplate.update("UPDATE \"user\" " +
                        "SET subscribedPublishersIdsList = ARRAY_REMOVE(subscribedPublishersIdsList, ?) " +
                        "WHERE id = ?",
                pubId, fromSubId);
    }

}
