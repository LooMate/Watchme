package com.easy2remember.poster_back.dao.impl;

import com.easy2remember.poster_back.dao.util.PostAnalyticsSnapRepo;
import com.easy2remember.poster_back.entity.impl.PostAnalyticsSnap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            List.of((Long) rs.getArray("viewersId").getArray()),
            List.of((Long) rs.getArray("refViewersId").getArray()), rs.getLong("postRates")
    );

    public JdbcPostAnalyticsSnapRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(PostAnalyticsSnap pa) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO postAnalyticsSnap VALUES(?,?,?,?,?,?,?,?,?)"
                    , RETURN_GENERATED_KEYS);

            ps.setLong(1, pa.getId());
            ps.setTimestamp(2, Timestamp.valueOf(pa.getCreatedAt()));
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

        return Optional.ofNullable(kh.getKey()).orElseGet(() -> -1).longValue();
    }

    @Override
    public Optional<PostAnalyticsSnap> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM postAnalyticsSnap WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public PostAnalyticsSnap[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM postAnalyticsSnap", this.rowMapper)
                .toArray(PostAnalyticsSnap[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM postAnalyticsSnap WHERE id = ?", id);
    }

    @Override
    public int updateById(PostAnalyticsSnap updatedPas, Long viewerId) {
        StringBuilder query = new StringBuilder();

        List<Object> parameters = new ArrayList<>();

        query.append(" lastChangedAt = " + LocalDateTime.now());

        if (updatedPas.getNumOfSpreads() != null) {
            query.append(", numOfSpreads = ?");
            parameters.add(updatedPas.getNumOfSpreads());
        }

        if (updatedPas.getViewedNum() != null) {
            query.append(", viewedNum = ?");
            parameters.add(updatedPas.getViewedNum());

            if (updatedPas.getViewedByReferralNum() != null) {
                query.append(", viewedByReferralNum = ?");
                parameters.add(updatedPas.getViewedByReferralNum());
            }

            if (updatedPas.getViewersId() != null) {
                query.append(", viewersId = ?");
                parameters.add(updatedPas.getViewersId());
            }

            if (updatedPas.getRefViewersId() != null) {
                query.append(", refViewersId = ?");
                parameters.add(updatedPas.getRefViewersId());
            }
        }
        if (updatedPas.getPostRates() != null) {
            query.append(", postRates = ?");
            parameters.add(updatedPas.getPostRates());
        }

        parameters.add(updatedPas.getId());

        return this.jdbcTemplate.update("UPDATE postAnalyticsSnap SET " + query + " WHERE id = ?", parameters);
    }


    @Override
    public PostAnalyticsSnap[] findAllPostAnalyticsSnapsByPostId(Long id) {
        return this.jdbcTemplate.query("SELECT * FROM postAnalyticsSnap WHERE id = ?",
                this.rowMapper, id).toArray(PostAnalyticsSnap[]::new);
    }

}
