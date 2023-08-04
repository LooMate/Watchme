package com.easy2remember.dao.impl;

import com.easy2remember.dao.util.TelegramChannelDetailsRepo;
import com.easy2remember.entity.impl.details.TelegramChannelDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTelegramChannelDetailsRepo extends JdbcAbstractRepo<TelegramChannelDetails>
        implements TelegramChannelDetailsRepo {

    private final RowMapper<TelegramChannelDetails> rowMapper = (rs, rowNum) -> new TelegramChannelDetails(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(), rs.getString("timeZone"),
            rs.getString("channelName"), rs.getString("channelBio"),
            rs.getString("channelLink"), rs.getLong("numberOfMembers"),
            rs.getString("typeOfChat"), List.of((Long[]) rs.getArray("adminsOfChannelIds").getArray()),
            rs.getLong("userId")
    );

    public JdbcTelegramChannelDetailsRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public long save(TelegramChannelDetails tch) {
        String sql = "INSERT INTO \"telegramChannelDetails\" (createdAt, lastChangedAt, timeZone, channelName, channelBio," +
                " channelLink, numberOfMembers, typeOfChat, adminsOfChannelIds, userId) VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(tch.getCreatedAt()));
            ps.setTimestamp(2, Timestamp.valueOf(tch.getLastChangedAt()));
            ps.setString(3, tch.getTimeZone());
            ps.setString(4, tch.getChannelName());
            ps.setString(5, tch.getChannelBio());
            ps.setString(6, tch.getChannelLink());
            ps.setLong(7, tch.getNumberOfMembers());
            ps.setString(8, tch.getTypeOfChat());
            ps.setArray(9, con.createArrayOf("bigint", tch.getAdminsOfChannelIds().toArray()));
            ps.setLong(10, tch.getUserId());
            return ps;
        };
        return jdbcTemplate.update(psc);
    }

    @Override
    public Optional<TelegramChannelDetails> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"telegramChannelDetails\" " +
                "WHERE id = ?", rowMapper, id));
    }

    @Override
    public Optional<TelegramChannelDetails> findByUsername(String name) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                "SELECT * FROM \"telegramChannelDetails\" " +
                        "WHERE id = (SELECT id FROM \"user\" WHERE name = ?)", rowMapper, name));
    }

    @Override
    public Optional<TelegramChannelDetails> findByUserId(Long userId) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"telegramChannelDetails\" " +
                "WHERE userId = ?", rowMapper, userId));
    }

    @Override
    public TelegramChannelDetails[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM \"telegramChannelDetails\"", this.rowMapper, null)
                .toArray(TelegramChannelDetails[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM \"telegramChannelDetails\" WHERE id = ?", id);
    }

    @Override // FIXME: 6/6/2023 maybe change bigint to UUID
    public int updateById(TelegramChannelDetails updatedTcd, Long id) {

        StringBuilder query = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        // reducing the size of a query
        // creating query according to existence of entity's data

        query.append(" lastChangedAt = '" + Timestamp.valueOf(updatedTcd.getLastChangedAt()) + "'");

        if (updatedTcd.getAdminsOfChannelIds() != null) {
            query.append(", adminsOfChannelIds = ?");
        }
        if (updatedTcd.getTimeZone() != null) {
            query.append(", timeZone = ?");
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

        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con
                    .prepareStatement("UPDATE \"telegramChannelDetails\" SET " + query + " WHERE id = ?");

            int parameterIndex = 1;
            if(updatedTcd.getAdminsOfChannelIds() != null) {
                ps.setArray(parameterIndex++, con.createArrayOf("bigint",
                        updatedTcd.getAdminsOfChannelIds().toArray()));
            }

            for (Object parameter : parameters) {
                ps.setObject(parameterIndex, parameter);
                parameterIndex++;
            }
            ps.setLong(parameterIndex, id);
            return ps;
        };

        return jdbcTemplate.update(psc);
    }
}
