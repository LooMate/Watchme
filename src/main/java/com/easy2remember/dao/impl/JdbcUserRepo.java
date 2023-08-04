package com.easy2remember.dao.impl;

import com.easy2remember.dao.util.UserRepo;
import com.easy2remember.entity.impl.main.Role;
import com.easy2remember.entity.impl.main.User;
import com.easy2remember.enums.InvitedFromType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepo extends JdbcAbstractRepo<User> implements UserRepo {

    private final RowMapper<User> rowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("lastChangedAt").toLocalDateTime(),
                    rs.getString("timeZone"), rs.getString("username"),
                    rs.getString("password"), rs.getString("email"),
                    rs.getString("usedReferralCode"), rs.getString("referralCode"),
                    List.of((Long[]) rs.getArray("postsIdsList").getArray()),
                    List.of((Long[]) rs.getArray("invitedUsersIdsList").getArray()),
                    List.of((Long[]) rs.getArray("subscribersIdsList").getArray()),
                    List.of((Long[]) rs.getArray("subscribedPublishersIdsList").getArray()),
                    rs.getBoolean("isInvited"),
                    InvitedFromType.valueOf(rs.getString("invitedFrom").toUpperCase()),
                    rs.getBoolean("isPremium"),
                    rs.getBoolean("isEnabled"));


    public JdbcUserRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(User user) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO \"user\" (createdAt,lastChangedAt," +
                    "timeZone, username, password, email, usedReferralCode, referralCode, postsIdsList, invitedUsersIdsList," +
                    "subscribersIdsList, subscribedPublishersIdsList, isInvited, invitedFrom, isPremium, isEnabled) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(2, Timestamp.valueOf(user.getLastChangedAt()));
            ps.setString(3, user.getTimeZone());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getUsedReferralCode());
            ps.setString(8, user.getReferralCode());
            ps.setArray(9, con.createArrayOf("bigint", new Long[0]));
            ps.setArray(10, con.createArrayOf("bigint", new Long[0]));
            ps.setArray(11, con.createArrayOf("bigint", new Long[0]));
            ps.setArray(12, con.createArrayOf("bigint", new Long[0]));
            ps.setBoolean(13, user.isInvited());
            ps.setString(14, user.getInvitedFrom().toString());
            ps.setBoolean(15, user.isPremium());
            ps.setBoolean(16, user.isEnabled());
            return ps;
        };

        KeyHolder kh = new GeneratedKeyHolder();
        this.jdbcTemplate.update(psc, kh);
        return Optional.ofNullable((Long) kh.getKeyList().get(0).get("id")).orElseGet(() -> -1L);
    }


    public Optional<User> findByGoogleSubIdBySub(String ggId) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"user\" WHERE id = " +
                "(SELECT userId FROM \"user_ggAcc\" WHERE googleId = ?) ", this.rowMapper, ggId));
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

    public int changeUserPassword(Character[] newPass, Long userId) {
        return this.jdbcTemplate.update("UPDATE \"user\" SET password = ? WHERE id = ?", newPass, userId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public int saveReferralIdToInvitor(Long referralId, Long invitorId) {
        return this.jdbcTemplate.update("UPDATE \"user\" SET invitedUsersIdsList = ARRAY_APPEND(invitedUsersIdsList, ?) " +
                "WHERE id = ?", referralId, invitorId);
    }

    @Override
    public int saveSubscriberToPublisherById(Long subId, Long toPubId) {
        return this.jdbcTemplate.update("UPDATE \"user\" SET subscribersIdsList = ARRAY_APPEND(subscribersIdsList, ?) " +
                        "WHERE id = ?",
                subId, toPubId);
    }

    @Override
    public int savePublisherIdToSubscriber(Long pubId, Long toSubId) {
        return this.jdbcTemplate.update("UPDATE \"user\" " +
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
        return this.jdbcTemplate.query("SELECT * FROM \"user\" WHERE id IN (SELECT UNNEST(subscribersIdsList) " +
                        "FROM \"user\" WHERE id = ?)",
                this.rowMapper, publisherId).toArray(User[]::new);
    }

    @Override
    public int removeSubscriberFromPublisherById(Long subId, Long fromPubId) {
        return this.jdbcTemplate.update("UPDATE \"user\" SET subscribersIdsList = ARRAY_REMOVE(subscribersIdsList, ?) " +
                        "WHERE id = ?",
                subId, fromPubId);
    }

    @Override
    public int removePublisherIdFromSubscriber(Long pubId, Long fromSubId) {
        return this.jdbcTemplate.update("UPDATE \"user\" " +
                        "SET subscribedPublishersIdsList = ARRAY_REMOVE(subscribedPublishersIdsList, ?) " +
                        "WHERE id = ?",
                pubId, fromSubId);
    }

    public int enableUser(Long userId) {
        return this.jdbcTemplate.update("UPDATE \"user\" SET isEnabled = TRUE WHERE id = ?", userId);
    }


    //------------------------------------------------------------------------------------------------------

    RowMapper<Role> roleRowMapper = (rs, colNum) -> new Role(rs.getLong("id"), rs.getString("name"));

    @Transactional(propagation = Propagation.REQUIRED)
    public int saveRoleForUserByUserId(List<String> rolesNames, Long userId, String username) {
        return this.jdbcTemplate.update("INSERT INTO \"user_roles\" VALUES(?, ?, ?)",
                userId, username, findRolesIdsByNames(rolesNames));
    }


    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Long[] findRolesIdsByNames(List<String> names) {
        return this.jdbcTemplate.query(con -> {
                    var ps = con.prepareStatement("SELECT id FROM \"role\" WHERE name = ANY (?)");
                    ps.setArray(1, con.createArrayOf("varchar", names.toArray()));
                    return ps;
                }, (rs, rowNum) -> rs.getLong("id")
        ).toArray(Long[]::new);

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Role[] findRolesForUserByUserId(Long userId) {
        return this.jdbcTemplate
                .query("SELECT * FROM \"role\" WHERE id IN " +
                        "(SELECT UNNEST(authoritiesArray) FROM \"user_roles\" where userId = ?)", roleRowMapper, userId)
                .toArray(Role[]::new);
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.SERIALIZABLE)
    public int updateUsersRolesByUserId(Long userId, List<String> roles) {
        return this.jdbcTemplate.update("UPDATE \"user_roles\" SET authoritiesArray = ? WHERE userId = ?",
                roles.toArray(), userId);
    }
}






