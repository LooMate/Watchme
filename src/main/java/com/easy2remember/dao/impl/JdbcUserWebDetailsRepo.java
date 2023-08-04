package com.easy2remember.dao.impl;

import com.easy2remember.dao.util.UserWebDetailsRepo;
import com.easy2remember.entity.impl.details.UserWebDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserWebDetailsRepo extends JdbcAbstractRepo<UserWebDetails> implements UserWebDetailsRepo {

    private final RowMapper<UserWebDetails> rowMapper = (rs, rowNum) -> new UserWebDetails(
            rs.getLong("id"),
            rs.getTimestamp("createdAt").toLocalDateTime(),
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
        return this.jdbcTemplate.update("INSERT INTO \"userWebDetails\" (createdAt, lastChangedAt, userBrowser," +
                        " browserLanguage, operationSystem, location, screenSizeAndColorDepth, timeZoneOffset," +
                        " timeZone, webVendorAndRenderGpu, cpuName, cpuCoreNum, deviceMemory, touchSupport," +
                        " adBlockerUsed, userId)" +
                        " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                Timestamp.valueOf(uwd.getCreatedAt()), Timestamp.valueOf(uwd.getLastChangedAt()),
                uwd.getUserBrowser(), uwd.getBrowserLanguage(), uwd.getOperationSystem(),
                uwd.getLocation(), uwd.getScreenSizeAndColorDepth(), uwd.getTimeZoneOffset(), uwd.getTimeZone(),
                uwd.getWebVendorAndRenderGpu(), uwd.getCpuName(), uwd.getCpuCoreNum(), uwd.getDeviceMemory(),
                uwd.isTouchSupport(), uwd.isAdBlockerUsed(), uwd.getUserId()
        );
    }

    @Override
    public Optional<UserWebDetails> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"userWebDetails\" WHERE id = ?",
                this.rowMapper, id));
    }

    @Override
    public Optional<UserWebDetails> findByUsername(String userName) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"userWebDetails\" WHERE userId = " +
        "(SELECT id FROM \"user\" WHERE username = ?)", this.rowMapper, userName));
    }

    @Override
    public Optional<UserWebDetails> findByUserId(Long userId) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT * FROM \"userWebDetails\" WHERE userId = ?",
                this.rowMapper, userId));
    }

    @Override
    public UserWebDetails[] findAll() {
        return this.jdbcTemplate.query("SELECT * FROM \"userWebDetails\"", this.rowMapper, null)
                .toArray(UserWebDetails[]::new);
    }

    @Override  // FIXME: 6/6/2023 maybe change bigint to UUID
    public int updateById(UserWebDetails updatedUwd, Long id) {
        StringBuilder query = new StringBuilder();

        List<Object> parameters = new ArrayList<>();

        query.append("lastChangedAt = '" + Timestamp.valueOf(updatedUwd.getLastChangedAt()) + "'");


        // reducing the size of a query
        // creating query according to existence of entity's data

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
        if (updatedUwd.getTimeZoneOffset() != 0) {
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
        if (updatedUwd.getCpuCoreNum() != 0) {
            query.append(", cpuCoreNum = ?");
            parameters.add(updatedUwd.getCpuCoreNum());
        }
        if (updatedUwd.getDeviceMemory() != 0) {
            query.append(", deviceMemory = ?");
            parameters.add(updatedUwd.getDeviceMemory());
        }
        query.append(", touchSupport = " + updatedUwd.isTouchSupport());
        query.append(", adBlockerUsed= " + updatedUwd.isAdBlockerUsed());

        parameters.add(id);

        return this.jdbcTemplate.update("UPDATE \"userWebDetails\" SET " + query + " WHERE id = ?",
                parameters.toArray());
    }

    @Override
    public int deleteById(Long id) {
        return this.jdbcTemplate.update("DELETE FROM \"userWebDetails\" WHERE id = ?", id);
    }
}
