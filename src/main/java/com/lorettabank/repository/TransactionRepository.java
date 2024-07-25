package com.lorettabank.repository;

import com.lorettabank.entity.Transaction;
import com.lorettabank.entity.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Transaction> TRANSACTION_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Transaction.builder()
                    .id(rs.getLong("id"))
                    .account(Account.builder()
                            .id(rs.getLong("account_id"))
                            .build())
                    .type(rs.getString("type"))
                    .amount(rs.getDouble("amount"))
                    .date(rs.getObject("date", LocalDateTime.class))
                    .description(rs.getString("description"))
                    .journalType(rs.getString("journal_type"))
                    .build();
        }
    };

    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_id, type, amount, date, description, journal_type) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getAccount().getId(), transaction.getType(), transaction.getAmount(), transaction.getDate(), transaction.getDescription(), transaction.getJournalType());
    }

    public Optional<Transaction> findById(Long id, Long userId) {
        String sql = "SELECT t.*, a.id AS account_id, a.account_number, a.name, a.account_type, a.available_balance, a.latest_balance, a.account_status, a.image_url, a.user_id " +
                "FROM transactions t " +
                "JOIN accounts a ON t.account_id = a.id " +
                "WHERE t.id = ? AND a.user_id = ?";
        List<Transaction> transactions = jdbcTemplate.query(sql, TRANSACTION_ROW_MAPPER, id, userId);
        return transactions.stream().findFirst();
    }

    public List<Transaction> findAll(Long userId) {
        String sql = "SELECT t.*, a.id AS account_id, a.account_number, a.name, a.account_type, a.available_balance, a.latest_balance, a.account_status, a.image_url, a.user_id " +
                "FROM transactions t " +
                "JOIN accounts a ON t.account_id = a.id " +
                "WHERE a.user_id = ?";
        return jdbcTemplate.query(sql, TRANSACTION_ROW_MAPPER, userId);
    }

    public void deleteById(Long id, Long userId) {
        String sql = "DELETE FROM transactions WHERE id = ? AND account_id IN (SELECT id FROM accounts WHERE user_id = ?)";
        jdbcTemplate.update(sql, id, userId);
    }
}
