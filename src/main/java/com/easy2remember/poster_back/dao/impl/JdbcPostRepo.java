package com.easy2remember.poster_back.dao.impl;

import com.easy2remember.poster_back.dao.util.PostRepo;
import com.easy2remember.poster_back.entity.impl.Post;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class JdbcPostRepo extends JdbcAbstractRepo<Post> implements PostRepo {

    private final RowMapper<Post> rowMapper = (rs, rowNum) -> new Post(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getString("timeZone"), rs.getTimestamp("lastChangedAt").toLocalDateTime(),
            rs.getString("postName"), rs.getString("postLink"), rs.getInt("priority"),
            rs.getBoolean("isExclusive"),
            List.of((Long) rs.getArray("usersWithAccess").getArray()), rs.getBoolean("isHot"),
            rs.getBytes("previewImage"), rs.getLong("postAnalyticsInfoId"),
            new PostAnalyticsSnap(
                    rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("lastChangedAt").toLocalDateTime(),
                    rs.getLong("numOfSpreads"), rs.getLong("viewedNum"),
                    rs.getLong("viewedByReferralNum"),
                    List.of((Long) rs.getArray("viewersId").getArray()),
                    List.of((Long) rs.getArray("refViewersId").getArray()), rs.getLong("postRates")
            )
    );


    public JdbcPostRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(Post post) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO post VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    RETURN_GENERATED_KEYS);
            ps.setLong(1, post.getId());
            ps.setTimestamp(2, Timestamp.valueOf(post.getCreatedAt()));
            ps.setTimestamp(3, Timestamp.valueOf(post.getLastChangedAt()));
            ps.setString(4, post.getPostName());
            ps.setString(5, post.getPostLink());
            ps.setInt(6, post.getPriority());
            ps.setBoolean(7, post.isExclusive());
            ps.setArray(8, con.createArrayOf("bigint", post.getUsersIdWithAccess().toArray()));
            ps.setBoolean(9, post.isHot());
            ps.setBytes(10, post.getPreviewImage());
            ps.setLong(11, post.getPostAnalyticsInfoId());
            ps.setLong(12, post.getPostAnalyticsSnap().getId());

            return ps;
        };

        KeyHolder kh = new GeneratedKeyHolder();
        long id = this.jdbcTemplate.update(psc, kh);
        return Optional.ofNullable(kh.getKey()).orElseGet(() -> -1).longValue();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM post WHERE id = ?",
                this.rowMapper, id));
    }


    @Override
    public Post[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM post", this.rowMapper, null).toArray(Post[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM post WHERE id = ?", id);
    }

    @Override
    public int updateById(Post post, Long id) {
        return this.jdbcTemplate.update("UPDATE post SET lastChangedAt = ?, postName = ?, priority = ?," +
                        "isExclusive = ?, isHot = ?, previewImage = ?",
                LocalDateTime.now(), post.getPostName(), post.getPriority(), post.isExclusive(), post.isHot(),
                post.getPreviewImage());
    }

    @Override
    public Post[] findAllPostsForToday() {
        return this.jdbcTemplate.query("SELECT * FROM post WHERE createdAt = ?", this.rowMapper,
                LocalDate.now().toString()).toArray(Post[]::new);
    }

}
