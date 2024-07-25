package com.lorettabank.repository;

import com.lorettabank.entity.Account;
import com.lorettabank.entity.AccountStatus;
import com.lorettabank.entity.AccountType;
import com.lorettabank.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final RowMapper<Account> ROW_MAPPER = (ResultSet rs, int rowNum) -> Account.builder()
            .id(rs.getInt("id"))
            .accountNumber(rs.getString("account_number"))
            .name(rs.getString("name"))
            .user(new User()) // Handle the user mapping as needed
            .accountType(AccountType.valueOf(rs.getString("account_type")))
            .availableBalance(rs.getDouble("available_balance"))
            .latestBalance(rs.getDouble("latest_balance"))
            .accountStatus(AccountStatus.valueOf(rs.getString("account_status")))
            .imageUrl(rs.getString("image_url"))
            .createdAt(rs.getObject("created_at", LocalDateTime.class))
            .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
            .build();

    public void save(Account account) {
        jdbcTemplate.update(
                "INSERT INTO accounts (account_number, name, user_id, account_type, available_balance, latest_balance, account_status, image_url, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                account.getAccountNumber(), account.getName(), account.getUser().getId(), account.getAccountType().name(), account.getAvailableBalance(), account.getLatestBalance(), account.getAccountStatus().name(), account.getImageUrl(), account.getCreatedAt(), account.getUpdatedAt()
        );
    }

    public Optional<Account> findById(Integer id) {
        return jdbcTemplate.query("SELECT * FROM accounts WHERE id = ?", ROW_MAPPER, id)
                .stream()
                .findFirst();
    }

    public List<Account> findByUserId(String userId) {
        return jdbcTemplate.query("SELECT * FROM accounts WHERE user_id = ?", ROW_MAPPER, userId);
    }

    public void update(Account account) {
        jdbcTemplate.update(
                "UPDATE accounts SET account_number = ?, name = ?, account_type = ?, available_balance = ?, latest_balance = ?, account_status = ?, image_url = ?, updated_at = ? WHERE id = ?",
                account.getAccountNumber(), account.getName(), account.getAccountType().name(), account.getAvailableBalance(), account.getLatestBalance(), account.getAccountStatus().name(), account.getImageUrl(), account.getUpdatedAt(), account.getId()
        );
    }

    public void delete(Integer id, String userId) {
        jdbcTemplate.update("DELETE FROM accounts WHERE id = ? AND user_id = ?", id, userId);
    }
}
