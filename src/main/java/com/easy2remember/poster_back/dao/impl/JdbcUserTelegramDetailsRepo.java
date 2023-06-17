package com.easy2remember.poster_back.dao.impl;

import com.easy2remember.poster_back.dao.util.UserTelegramDetailsRepo;
import com.easy2remember.poster_back.entity.impl.UserTelegramDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserTelegramDetailsRepo extends JdbcAbstractRepo<UserTelegramDetails> implements UserTelegramDetailsRepo {

    private final RowMapper<UserTelegramDetails> rowMapper = (rs, rowNum) -> new UserTelegramDetails(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(), rs.getString("timeZone"),
            rs.getString("firstName"), rs.getString("lastname"),
            rs.getString("username"), rs.getLong("chatId"),
            rs.getBoolean("isReachableByPrivateForwards"), rs.getBoolean("isPremium"),
            rs.getLong("userId")
    );

    public JdbcUserTelegramDetailsRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    public long save(UserTelegramDetails utd) {
        return this.jdbcTemplate.update("INSERT INTO userTelegramDetails VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                utd.getId(), Timestamp.valueOf(utd.getCreatedAt()), Timestamp.valueOf(utd.getLastChangedAt()),
                utd.getTimeZone(), utd.getFirstName(), utd.getUsername(), utd.getChatId(),
                utd.isReachableByPrivateForwards(), utd.isPremium(), utd.getUserId()
        );
    }

    @Override
    public Optional<UserTelegramDetails> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM userTelegramDetails WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public Optional<UserTelegramDetails> findByName(String name) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM userTelegramDetails WHERE id = " +
                        "(SELECT id FROM \"user\" WHERE username = ?)",
                this.rowMapper, name));
    }

    @Override
    public UserTelegramDetails[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM userTelegramDetails", this.rowMapper, null)
                .toArray(UserTelegramDetails[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM userTelegramDetails WHERE id = ?", id);
    }

    @Transactional()
    @Override // FIXME: 6/6/2023 maybe change bigint to UUID
    public int updateById(UserTelegramDetails updatedUtd, Long id) {
        StringBuilder query = new StringBuilder();

        List<Object> parameters = new ArrayList<>();

        query.append("lastChangedAt = " + LocalDateTime.now());

        if (updatedUtd.getTimeZone() != null) {
            query.append(", timeZone = ?");
            parameters.add(updatedUtd.getTimeZone());
        }
        if (updatedUtd.getFirstName() != null) {
            query.append(", firstName = ?");
            parameters.add(updatedUtd.getFirstName());
        }
        if (updatedUtd.getLastName() != null) {
            query.append(", lastName = ?");
            parameters.add(updatedUtd.getLastName());
        }
        if (updatedUtd.getUsername() != null) {
            query.append(", username = ?");
            parameters.add(updatedUtd.getUsername());
        }
        if (updatedUtd.getChatId() != null) {
            query.append(", chatId = ?");
            parameters.add(updatedUtd.getChatId());
        }
        query.append(", isReachableByPrivateForwards = " + updatedUtd.isReachableByPrivateForwards());
        query.append(", isPremium = " + updatedUtd.isPremium());

        return this.jdbcTemplate.update("UPDATE userTelegramDetails SET " + query + "WHERE id = ?", id);
    }

    @Override
    public Long findChatIdByUserTgDetId(Long userTgDetId) {
        return this.jdbcTemplate.queryForObject("SELECT chatId FROM userTelegramDetails WHERE id = ?", Long.class);
    }

    @Override
    public String findUsernameById(Long userTgDetId) {
        return this.jdbcTemplate.queryForObject("SELECT username FROM userTelegramDetails WHERE id = ?",
                String.class, userTgDetId);
    }

    @Override
    public Long findUserTgDetByUserId(Long userId) {
        return this.jdbcTemplate.queryForObject("SELECT id FROM userTelegramDetails WHERE userId = ?",
                Long.class, userId);
    }
}
