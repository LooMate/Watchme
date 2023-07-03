package com.easy2remember.daomodule.dao.impl;

import com.easy2remember.daomodule.dao.util.PostAnalyticsInfoRepo;
import com.easy2remember.entitymodule.entity.impl.PostAnalyticsInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPostAnalyticsInfoRepo extends JdbcAbstractRepo<PostAnalyticsInfo> implements PostAnalyticsInfoRepo {

    private final RowMapper<PostAnalyticsInfo> rowMapper = (rs, rowNum) -> new PostAnalyticsInfo(
            rs.getLong("id"),
            rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(),
            rs.getBoolean("isActive"),
            rs.getInt("analyticsFrequencyInHour"),
            List.of((Long[]) rs.getArray("postAnalyticsSnapsIdList").getArray()));


    public JdbcPostAnalyticsInfoRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(PostAnalyticsInfo pa) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO \"postAnalyticsInfo\"(createdAt, lastCHangedAt, isActive," +
                    "analyticsFrequencyInHour, postAnalyticsSnapsIdList) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setTimestamp(1, Timestamp.valueOf(pa.getCreatedAt()));
            ps.setTimestamp(2, Timestamp.valueOf(pa.getLastChangedAt()));
            ps.setBoolean(3, pa.isActive());
            ps.setInt(4, pa.getAnalyticsFrequencyInHour());
            ps.setArray(5, con.createArrayOf("bigint", pa.getPostAnalyticsSnapsIdList().toArray()));

            return ps;
        };
        var kh = new GeneratedKeyHolder();
        this.jdbcTemplate.update(psc, kh);

        return Optional.ofNullable((Long) kh.getKeyList().get(0).get("id")).orElseGet(() -> -1L);
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    @Override
    public Optional<PostAnalyticsInfo> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"postAnalyticsInfo\"  WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public Long findPostAnalyticsInfoIdByPostId(Long postId) {
        return this.jdbcTemplate.queryForObject("SELECT postAnalyticsInfoId FROM \"post\" WHERE id = ?",
                Long.class, postId);
    }

    @Override
    public PostAnalyticsInfo[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM \"postAnalyticsInfo\" ", this.rowMapper)
                .toArray(PostAnalyticsInfo[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM \"postAnalyticsInfo\"  WHERE id = ?", id);
    }

    @Override
    public int updateById(PostAnalyticsInfo pai, Long id) {
        return this.jdbcTemplate.update("UPDATE \"postAnalyticsInfo\" " +
                        "SET isActive = ?, analyticsFrequencyInHour = ? WHERE id = ?",
                pai.isActive(), pai.getAnalyticsFrequencyInHour(), id);
    }

}
