package com.lorettabank.repository;

import com.lorettabank.entity.Card;
import com.lorettabank.entity.User;
import com.lorettabank.entity.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Card> CARD_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("user_id"));  // Ensure User ID is String

            Account account = new Account();
            account.setId(rs.getInt("account_id"));  // Ensure Account ID is Integer

            return Card.builder()
                    .id(rs.getInt("id"))
                    .user(user)
                    .account(account)
                    .cardNumber(rs.getString("card_number"))
                    .expiryDate(rs.getObject("expiry_date", LocalDate.class))
                    .cvv(rs.getString("cvv"))
                    .creditLimit(rs.getDouble("credit_limit"))
                    .balance(rs.getDouble("balance"))
                    .createdAt(rs.getObject("created_at", LocalDateTime.class))
                    .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                    .build();
        }
    };

    public void save(Card card) {
        String sql = "INSERT INTO cards (user_id, account_id, card_number, expiry_date, cvv, credit_limit, balance, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, card.getUser().getId(), card.getAccount().getId(), card.getCardNumber(), card.getExpiryDate(), card.getCvv(), card.getCreditLimit(), card.getBalance(), card.getCreatedAt(), card.getUpdatedAt());
    }

    public Optional<Card> findById(Integer id, String userId) {
        String sql = "SELECT * FROM cards WHERE id = ? AND user_id = ?";
        List<Card> cards = jdbcTemplate.query(sql, CARD_ROW_MAPPER, id, userId);
        return cards.stream().findFirst();
    }

    public List<Card> findAll(String userId) {
        String sql = "SELECT * FROM cards WHERE user_id = ?";
        return jdbcTemplate.query(sql, CARD_ROW_MAPPER, userId);
    }

    public void update(Card card) {
        String sql = "UPDATE cards SET card_number = ?, expiry_date = ?, cvv = ?, credit_limit = ?, balance = ?, updated_at = ? WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, card.getCardNumber(), card.getExpiryDate(), card.getCvv(), card.getCreditLimit(), card.getBalance(), card.getUpdatedAt(), card.getId(), card.getUser().getId());
    }

    public void deleteById(Integer id, String userId) {
        String sql = "DELETE FROM cards WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }
}
