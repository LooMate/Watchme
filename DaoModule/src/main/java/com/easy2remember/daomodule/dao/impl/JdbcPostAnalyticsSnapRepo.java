package com.easy2remember.daomodule.dao.impl;

import com.easy2remember.daomodule.dao.util.PostAnalyticsSnapRepo;
import com.easy2remember.entitymodule.entity.impl.PostAnalyticsSnap;
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

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class JdbcPostAnalyticsSnapRepo extends JdbcAbstractRepo<PostAnalyticsSnap> implements PostAnalyticsSnapRepo {

    private final RowMapper<PostAnalyticsSnap> rowMapper = (rs, rowNum) -> new PostAnalyticsSnap(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(),
            rs.getLong("numOfSpreads"), rs.getLong("viewedNum"),
            rs.getLong("viewedByReferralNum"),
            List.of((Long[]) rs.getArray("viewersId").getArray()),
            List.of((Long[]) rs.getArray("refViewersId").getArray()), rs.getLong("postRates")
    );

    public JdbcPostAnalyticsSnapRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(PostAnalyticsSnap pa) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO \"postAnalyticsSnap\" (createdAt, " +
                            "lastChangedAt, numOfSpreads, viewedNUm, viewedByReferralNum, viewersId, refViewersId," +
                            "postRates) VALUES(?,?,?,?,?,?,?,?)"
                    , RETURN_GENERATED_KEYS);

            ps.setTimestamp(1, Timestamp.valueOf(pa.getCreatedAt()));
            ps.setTimestamp(2, Timestamp.valueOf(pa.getLastChangedAt()));
            ps.setLong(3, pa.getNumOfSpreads());
            ps.setLong(4, pa.getViewedNum());
            ps.setLong(5, pa.getViewedByReferralNum());
            ps.setArray(6, con.createArrayOf("bigint", pa.getViewersId().toArray()));
            ps.setArray(7, con.createArrayOf("bigint", pa.getRefViewersId().toArray()));
            ps.setLong(8, pa.getPostRates());
            return ps;
        };

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, kh);

        return Optional.ofNullable((Long) kh.getKeyList().get(0).get("id")).orElseGet(() -> -1L);
    }

    @Override
    public Optional<PostAnalyticsSnap> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"postAnalyticsSnap\" WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public PostAnalyticsSnap[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM \"postAnalyticsSnap\"", this.rowMapper)
                .toArray(PostAnalyticsSnap[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM \"postAnalyticsSnap\" WHERE id = ?", id);
    }

    @Override
    public int updateById(PostAnalyticsSnap updatedPas, Long viewerId) {

        PreparedStatementCreator psc = con -> {

            PreparedStatement ps = con.prepareStatement("UPDATE \"postAnalyticsSnap\" SET lastChangedAt = ?," +
                            " numOfSpreads = ?, viewedNum = ?, viewedByReferralNum = ?, viewersId = ?, refViewersId = ?," +
                            " postRates = ? WHERE id = ?",
                    Statement.NO_GENERATED_KEYS);

            ps.setTimestamp(1, Timestamp.valueOf(updatedPas.getLastChangedAt()));
            ps.setLong(2, updatedPas.getNumOfSpreads());
            ps.setLong(3, updatedPas.getViewedNum());
            ps.setLong(4, updatedPas.getViewedByReferralNum());
            ps.setArray(5, con.createArrayOf("bigint", updatedPas.getViewersId().toArray()));
            ps.setArray(6, con.createArrayOf("bigint", updatedPas.getRefViewersId().toArray()));
            ps.setLong(7, updatedPas.getPostRates());
            ps.setLong(8, updatedPas.getId());
            return ps;
        };

        return this.jdbcTemplate.update(psc);
    }


    @Override
    public PostAnalyticsSnap[] findAllPostAnalyticsSnapsByPostId(List<Long> pasIds) {
        return this.jdbcTemplate.query("SELECT * FROM \"postAnalyticsSnap\" WHERE id IN (?)",
                this.rowMapper, pasIds.toArray()).toArray(PostAnalyticsSnap[]::new);
    }

}
