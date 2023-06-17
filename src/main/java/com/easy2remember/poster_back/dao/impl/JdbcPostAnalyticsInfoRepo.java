package com.easy2remember.poster_back.dao.impl;

import com.easy2remember.poster_back.dao.util.PostAnalyticsInfoRepo;
import com.easy2remember.poster_back.entity.impl.PostAnalyticsInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class JdbcPostAnalyticsInfoRepo extends JdbcAbstractRepo<PostAnalyticsInfo> implements PostAnalyticsInfoRepo {

    private final RowMapper<PostAnalyticsInfo> rowMapper = (rs, rowNum) -> new PostAnalyticsInfo(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(),
            rs.getBoolean("isActive"),rs.getInt("analyticsFrequencyInHour"),
            rs.getLong("postId"));


    public JdbcPostAnalyticsInfoRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(PostAnalyticsInfo pa) {
        return this.jdbcTemplate.update("INSERT INTO postAnalyticsInfo VALUES(?,?,?,?)",
                pa.getId(), Timestamp.valueOf(pa.getCreatedAt()), pa.isActive(),
                pa.getAnalyticsFrequencyInHour()
        );
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    @Override
    public Optional<PostAnalyticsInfo> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM postAnalyticsInfo WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public PostAnalyticsInfo[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM postAnalyticsInfo", this.rowMapper)
                .toArray(PostAnalyticsInfo[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM postAnalyticsInfo WHERE id = ?", id);
    }

    @Override
    public int updateById(PostAnalyticsInfo pai, Long id) {
        return this.jdbcTemplate.update("UPDATE postAnalyticsInfo " +
                        "SET isActive = ?, analyticsFrequencyInHour = ? WHERE id = ?",
                pai.isActive(), pai.getAnalyticsFrequencyInHour(), id);
    }

}
