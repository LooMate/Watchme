package com.easy2remember.dao.impl;

import com.easy2remember.dao.util.PostDetailsRepo;
import com.easy2remember.entity.impl.details.PostDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class JdbcPostDetailsRepo extends JdbcAbstractRepo<PostDetails> implements PostDetailsRepo {

    private final RowMapper<PostDetails> rowMapper = (rs, rowNum) -> new PostDetails(
            rs.getLong("id"),
            rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(),
            rs.getString("description"),
            rs.getBytes("image"),
            rs.getLong("postId")
    );


    public JdbcPostDetailsRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    public long save(PostDetails postDetails) {
        return this.jdbcTemplate.update("INSERT INTO \"postDetails\" (createdAt, lastChangedAt, description, image, postId)" +
                        " VALUES(?,?,?,?,?)",
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                postDetails.getDescription(), postDetails.getImage(), postDetails.getPostId()
        );
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    @Override
    public Optional<PostDetails> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"postDetails\" WHERE id = ?",
                this.rowMapper, id));
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Optional<PostDetails> findByPostId(Long postId) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"postDetails\" WHERE postId = ?",
                this.rowMapper, postId));
    }

    @Override
    public PostDetails[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM \"postDetails\" ", this.rowMapper, null)
                .toArray(PostDetails[]::new);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM \"postDetails\" WHERE id = ?", id);
    }

    @Override
    public int updateById(PostDetails pDet, Long id) {
        return this.jdbcTemplate.update("UPDATE \"postDetails\" SET lastChanedAt = ?, description = ?, image = ?",
                Timestamp.valueOf(pDet.getLastChangedAt()), pDet.getDescription(), pDet.getImage());
    }
}
