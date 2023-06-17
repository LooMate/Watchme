package com.easy2remember.poster_back.dao.impl;

import com.easy2remember.poster_back.dao.util.UserWebDetailsRepo;
import com.easy2remember.poster_back.entity.impl.UserWebDetails;
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
public class JdbcUserWebDetailsRepo extends JdbcAbstractRepo<UserWebDetails> implements UserWebDetailsRepo {

    private final RowMapper<UserWebDetails> rowMapper = (rs, rowNum) -> new UserWebDetails(
            rs.getLong("id"), rs.getTimestamp("createdAt").toLocalDateTime(),
            rs.getTimestamp("lastChangedAt").toLocalDateTime(),
            rs.getString("userBrowser"), rs.getString("browserLanguage"),
            rs.getString("operationSystem"), rs.getString("location"),
            rs.getString("screenSizeAndColorDepth"), rs.getInt("timeZoneOffset"),
            rs.getString("timeZone"), rs.getString("webVendorAndRenderGpu"),
            rs.getString("cpuName"), rs.getInt("cpuCoreNum"),
            rs.getInt("deviceMemory"), rs.getBoolean("touchSupport"),
            rs.getBoolean("adBlockerUsed"), rs.getLong("userId")
    );

    public JdbcUserWebDetailsRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public long save(UserWebDetails uwd) {
        return this.jdbcTemplate.update("INSERT INTO userWebDetails VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                uwd.getId(), Timestamp.valueOf(uwd.getCreatedAt()), Timestamp.valueOf(uwd.getLastChangedAt()),
                uwd.getUserBrowser(), uwd.getBrowserLanguage(), uwd.getOperationSystem(),
                uwd.getLocation(), uwd.getScreenSizeAndColorDepth(), uwd.getTimeZoneOffset(), uwd.getTimeZone(),
                uwd.getWebVendorAndRenderGpu(), uwd.getCpuName(), uwd.getCpuCoreNum(), uwd.getDeviceMemory(),
                uwd.isTouchSupport(), uwd.isAdBlockerUsed(), uwd.getUserId()
        );
    }

    @Override
    public Optional<UserWebDetails> findById(Long userId) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM userWebDetails WHERE userId = ?",
                this.rowMapper, userId));
    }

    @Override
    public Optional<UserWebDetails> findByName(String userName) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM userWebDetails WHERE userId = " +
                "(SELECT id FROM \"user\" WHERE username = ?)", this.rowMapper, userName));
    }

    @Override
    public UserWebDetails[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM userWebDetails", this.rowMapper, null)
                .toArray(UserWebDetails[]::new);
    }

    @Transactional
    @Override  // FIXME: 6/6/2023 maybe change bigint to UUID
    public int updateById(UserWebDetails updatedUwd, Long id) {
        StringBuilder query = new StringBuilder();

        List<Object> parameters = new ArrayList<>();

        query.append("lastChangedAt = " + LocalDateTime.now());

        if (updatedUwd.getUserBrowser() != null) {
            query.append(", userBrowser = ?");
            parameters.add(updatedUwd.getUserBrowser());
        }
        if (updatedUwd.getBrowserLanguage() != null) {
            query.append(", browserLanguage = ?");
            parameters.add(updatedUwd.getBrowserLanguage());
        }
        if (updatedUwd.getOperationSystem() != null) {
            query.append(", operationSystem = ?");
            parameters.add(updatedUwd.getOperationSystem());
        }
        if (updatedUwd.getLocation() != null) {
            query.append(", location = ?");
            parameters.add(updatedUwd.getLocation());
        }
        if (updatedUwd.getScreenSizeAndColorDepth() != null) {
            query.append(", screenSizeAndColorDepth = ?");
            parameters.add(updatedUwd.getScreenSizeAndColorDepth());
        }
        if (updatedUwd.getTimeZoneOffset() != -1) {
            query.append(", timeZoneOffset = ?");
            parameters.add(updatedUwd.getTimeZoneOffset());
        }
        if (updatedUwd.getTimeZone() != null) {
            query.append(", timeZone = ?");
            parameters.add(updatedUwd.getTimeZone());
        }
        if (updatedUwd.getWebVendorAndRenderGpu() != null) {
            query.append(", webVendorAndRenderGpu = ?");
            parameters.add(updatedUwd.getWebVendorAndRenderGpu());
        }
        if (updatedUwd.getCpuName() != null) {
            query.append(", cpuName = ?");
            parameters.add(updatedUwd.getCpuName());
        }
        if (updatedUwd.getCpuCoreNum() != -1) {
            query.append(", cpuCoreNum = ?");
            parameters.add(updatedUwd.getCpuCoreNum());
        }
        if (updatedUwd.getDeviceMemory() != -1) {
            query.append(", deviceMemory = ?");
            parameters.add(updatedUwd.getDeviceMemory());
        }
        query.append(", touchSupport = " + updatedUwd.isTouchSupport());
        query.append(", adBlockerUsed= " + updatedUwd.isAdBlockerUsed());

        parameters.add(updatedUwd.getId());

        return this.jdbcTemplate.update("UPDATE userWebDetails SET " + query + " WHERE id = ?", parameters);
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM userWebDetails WHERE id = ?", id);
    }

    @Override
    public Long findUserWebDetIdByUserId(Long userId) {
        return this.jdbcTemplate.queryForObject("SELECT id FROM userWebDetails WHERE userId = ?",
                Long.class, userId);
    }


}
