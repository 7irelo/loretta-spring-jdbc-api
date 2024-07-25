package com.lorettabank.repository;

import com.lorettabank.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<User> USER_ROW_MAPPER = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getString("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .address(rs.getString("address"))
                    .dateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class))
                    .occupation(rs.getString("occupation"))
                    .phone(rs.getString("phone"))
                    .email(rs.getString("email"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .createdAt(rs.getObject("created_at", java.time.LocalDateTime.class))
                    .updatedAt(rs.getObject("updated_at", java.time.LocalDateTime.class))
                    .build();
        }
    };

    public void save(User user) {
        String sql = "INSERT INTO users (id, first_name, last_name, address, date_of_birth, occupation, phone, email, username, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getDateOfBirth(), user.getOccupation(), user.getPhone(), user.getEmail(), user.getUsername(), user.getPassword(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, USER_ROW_MAPPER, username);
        return users.stream().findFirst();
    }

    public Optional<User> findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, USER_ROW_MAPPER, id);
        return users.stream().findFirst();
    }

    public void update(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, address = ?, date_of_birth = ?, occupation = ?, phone = ?, email = ?, username = ?, password = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getAddress(), user.getDateOfBirth(), user.getOccupation(), user.getPhone(), user.getEmail(), user.getUsername(), user.getPassword(), user.getUpdatedAt(), user.getId());
    }
}
