package com.lorettabank.repository;

import com.lorettabank.entity.CustomerSupport;
import com.lorettabank.entity.SupportStatus;
import com.lorettabank.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerSupportRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerSupportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<CustomerSupport> CUSTOMER_SUPPORT_ROW_MAPPER = new RowMapper<>() {
        @Override
        public CustomerSupport mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CustomerSupport.builder()
                    .id(rs.getInt("id"))
                    .user(new User(rs.getLong("user_id")))
                    .query(rs.getString("query"))
                    .response(rs.getString("response"))
                    .status(SupportStatus.valueOf(rs.getString("status")))
                    .createdAt(rs.getObject("created_at", LocalDateTime.class))
                    .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                    .build();
        }
    };

    public void save(CustomerSupport customerSupport) {
        String sql = "INSERT INTO customer_support (user_id, query, response, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customerSupport.getUser().getId(), customerSupport.getQuery(), customerSupport.getResponse(), customerSupport.getStatus().name(), customerSupport.getCreatedAt(), customerSupport.getUpdatedAt());
    }

    public Optional<CustomerSupport> findById(Integer id, Long userId) {
        String sql = "SELECT * FROM customer_support WHERE id = ? AND user_id = ?";
        List<CustomerSupport> supports = jdbcTemplate.query(sql, CUSTOMER_SUPPORT_ROW_MAPPER, id, userId);
        return supports.stream().findFirst();
    }

    public List<CustomerSupport> findAll(Long userId) {
        String sql = "SELECT * FROM customer_support WHERE user_id = ?";
        return jdbcTemplate.query(sql, CUSTOMER_SUPPORT_ROW_MAPPER, userId);
    }

    public void update(CustomerSupport customerSupport) {
        String sql = "UPDATE customer_support SET query = ?, response = ?, status = ?, updated_at = ? WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, customerSupport.getQuery(), customerSupport.getResponse(), customerSupport.getStatus().name(), customerSupport.getUpdatedAt(), customerSupport.getId(), customerSupport.getUser().getId());
    }

    public void deleteById(Integer id, Long userId) {
        String sql = "DELETE FROM customer_support WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }
}
