package com.easy2remember.dao.impl;

import com.easy2remember.dao.util.PostRepo;
import com.easy2remember.entity.impl.details.PostAnalyticsSnap;
import com.easy2remember.entity.impl.main.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class JdbcPostRepo extends JdbcAbstractRepo<Post> implements PostRepo {

    private final RowMapper<Post> rowMapper = (rs, rowNum) -> new Post(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(),
            rs.getString("postName"), rs.getString("postLink"),
            rs.getInt("priority"), rs.getBoolean("isExclusive"),
            List.of((Long[]) rs.getArray("usersIdWithAccess").getArray()),
            rs.getBoolean("isHot"), rs.getBytes("previewImage"),
            rs.getLong("postAnalyticsInfoId"),
            new PostAnalyticsSnap(
                    rs.getLong("id"),
                    rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("lastChangedAt").toLocalDateTime(),
                    rs.getLong("numOfSpreads"),
                    rs.getLong("viewedNum"),
                    rs.getLong("viewedByReferralNum"),
                    List.of((Long[]) rs.getArray("viewersId").getArray()),
                    List.of((Long[]) rs.getArray("refViewersId").getArray()),
                    rs.getLong("postRates")
            )
    );


    public JdbcPostRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public long save(Post post) {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO post(createdAt, lastChangedAt," +
                            " postName, postLink, priority, isExclusive, usersIdWithAccess, isHot, previewImage, " +
                            "postAnalyticsInfoId, postAnalyticsSnapId) " +
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                    RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, post.getPostName());
            ps.setString(4, post.getPostLink());
            ps.setInt(5, post.getPriority());
            ps.setBoolean(6, post.isExclusive());
            ps.setArray(7, con.createArrayOf("bigint", post.getUsersIdWithAccess().toArray()));
            ps.setBoolean(8, post.isHot());
            ps.setBytes(9, post.getPreviewImage());
            ps.setLong(10, post.getPostAnalyticsInfoId());
            ps.setLong(11, post.getPostAnalyticsSnap().getId());

            return ps;
        };

        var kh = new GeneratedKeyHolder();
        this.jdbcTemplate.update(psc, kh);
        return Optional.ofNullable((Long) kh.getKeyList().get(0).get("id")).orElseGet(() -> -1L);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"post\" " +
                        "LEFT JOIN \"postAnalyticsSnap\" ON postAnalyticsSnapId = \"postAnalyticsSnap\".id " +
                        "WHERE post.id = ?",
                this.rowMapper, id));
    }

    @Override
    public Post[] findAllByPublisherId(Long pubId) {
        return this.jdbcTemplate.query("SELECT * FROM \"post\" " +
                        "LEFT JOIN \"postAnalyticsSnap\" ON postAnalyticsSnapId = \"postAnalyticsSnap\".id " +
                        "WHERE post.id = ANY(SELECT UNNEST(postsIdsList) FROM \"user\" WHERE id = ?) " +
                        "ORDER BY isHot DESC, priority DESC",
                this.rowMapper, pubId).toArray(Post[]::new);
    }

    @Override
    public Post[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM \"post\" " +
                        "LEFT JOIN \"postAnalyticsSnap\" ON postAnalyticsSnapId = \"postAnalyticsSnap\".id " +
                        "ORDER BY priority DESC",
                this.rowMapper, null).toArray(Post[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM \"post\" WHERE id = ?", id);
    }

    @Override
    public int updateById(Post updatedPost, Long id) {

        StringBuilder query = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        query.append("lastChangedAt = '" + Timestamp.valueOf(updatedPost.getLastChangedAt()) + "'");

        // reducing the size of a query
        // creating query according to existence of entity's data
        if (updatedPost.getPostName() != null) {
            query.append(", postName = ?");
            parameters.add(updatedPost.getPostName());
        }
        if (updatedPost.getPreviewImage() != null) {
            query.append(", previewImage = ?");
            parameters.add(updatedPost.getPreviewImage());
        }
        parameters.add(updatedPost.getPriority());
        parameters.add(updatedPost.isExclusive());
        parameters.add(updatedPost.isHot());
        parameters.add(updatedPost.getId());

        return this.jdbcTemplate.update("UPDATE post SET " + query + " , priority = ?, isExclusive = ?, isHot = ?" +
                " WHERE id = ?", parameters.toArray());
    }

    @Override
    public Post[] findAllPostsForToday() {
        return this.jdbcTemplate.query("SELECT * FROM \"post\" " +
                        "LEFT JOIN \"postAnalyticsSnap\" ON postAnalyticsSnapId = \"postAnalyticsSnap\".id " +
                        "WHERE post.createdAt >= ?", this.rowMapper,
                Timestamp.valueOf(LocalDate.now() + " 00:00:00")).toArray(Post[]::new);
    }

}
