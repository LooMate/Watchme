package com.easy2remember.poster_back.dao.impl;

import com.easy2remember.poster_back.dao.util.TelegramChannelDetailsRepo;
import com.easy2remember.poster_back.entity.impl.TelegramChannelDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTelegramChannelDetailsRepo extends JdbcAbstractRepo<TelegramChannelDetails> implements TelegramChannelDetailsRepo {

    private final RowMapper<TelegramChannelDetails> rowMapper = (rs, rowNum) -> new TelegramChannelDetails(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(), rs.getString("timeZone"),
            rs.getString("channelName"), rs.getString("channelBio"),
            rs.getString("channelLink"), rs.getLong("numberOdMembers"),
            rs.getString("typeOfChat"), List.of((Long) rs.getArray("adminsOfChanelIds").getArray()),
            rs.getLong("userId")
    );

    public JdbcTelegramChannelDetailsRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public long save(TelegramChannelDetails tch) {
        return this.jdbcTemplate.update("INSERT INTO telegramChannelDetails VALUES (?,?,?,?,?,?,?,?,?,?)",
                tch.getId(), tch.getCreatedAt(), tch.getLastChangedAt(), tch.getChannelName(), tch.getChannelBio(),
                tch.getChannelLink(), tch.getNumberOfMembers(), tch.getTypeOfChat(), tch.getAdminsOfChannelIds());
    }

    @Override
    public Optional<TelegramChannelDetails> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM telegramChannelDetails " +
                "WHERE id = ?", rowMapper, id));
    }

    @Override
    public Optional<TelegramChannelDetails> findByName(String name) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                "SELECT * FROM telegramChannelDetails " +
                        "WHERE id = (SELECT id FROM \"user\" WHERE name = ?)", rowMapper, name));
    }

    @Override
    public TelegramChannelDetails[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM adminsOfChannelIds", this.rowMapper,null)
                .toArray(TelegramChannelDetails[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM telegramChannelDetails WHERE id = ?", id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override // FIXME: 6/6/2023 maybe change bigint to UUID
    public int updateById(TelegramChannelDetails updatedTcd, Long id) {
        StringBuilder query = new StringBuilder();

        List<Object> parameters = new ArrayList<>();

        query.append(" lastChangedAt = " + updatedTcd.getLastChangedAt());

        if (updatedTcd.getTimeZone() != null) {
            query.append(",timeZone = ?");
            parameters.add(updatedTcd.getTimeZone());
        }
        if (updatedTcd.getChannelName() != null) {
            query.append(", channelName = ?");
            parameters.add(updatedTcd.getChannelName());
        }
        if (updatedTcd.getChannelBio() != null) {
            query.append(", channelBio = ?");
            parameters.add(updatedTcd.getChannelBio());
        }
        if (updatedTcd.getChannelLink() != null) {
            query.append(", channelLink = ?");
            parameters.add(updatedTcd.getChannelLink());
        }
        if (updatedTcd.getNumberOfMembers() != null) {
            query.append(", numberOfMembers = ?");
            parameters.add(updatedTcd.getNumberOfMembers());
        }
        if (updatedTcd.getTypeOfChat() != null) {
            query.append(", typeOfChat = ?");
            parameters.add(updatedTcd.getTypeOfChat());
        }
        if (updatedTcd.getAdminsOfChannelIds() != null) {
            query.append(", adminsOfChannelIds = ?");
            parameters.add(updatedTcd.getAdminsOfChannelIds());
        }

        parameters.add(updatedTcd.getId());

        return this.jdbcTemplate.update("UPDATE telegramChannelDetails SET " + query + " WHERE id = ?", parameters);
    }

    @Override
    public Long[] findAllAdminsOfChannelByChannelId(Long channelId) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM telegramChannelDetails WHERE id = ?",
                Long[].class, channelId);
    }

    @Override
    public Long findTgChIdDetByUserId(Long userId) {
        return this.jdbcTemplate.queryForObject("SELECT id FROM telegramChannelDetails WHERE userId = ?",
                Long.class, userId);
    }
}
