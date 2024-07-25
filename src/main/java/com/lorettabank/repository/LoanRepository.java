package com.lorettabank.repository;

import com.lorettabank.entity.Loan;
import com.lorettabank.entity.LoanType;
import com.lorettabank.entity.User;
import com.lorettabank.entity.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LoanRepository {

    private final JdbcTemplate jdbcTemplate;

    public LoanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Loan> LOAN_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Loan.builder()
                    .id(rs.getLong("id"))
                    .user(new User(rs.getLong("user_id")))
                    .account(new Account(rs.getLong("account_id")))
                    .loanType(LoanType.valueOf(rs.getString("loan_type")))
                    .amount(rs.getDouble("amount"))
                    .interestRate(rs.getDouble("interest_rate"))
                    .term(rs.getInt("term"))
                    .startDate(rs.getObject("start_date", LocalDate.class))
                    .endDate(rs.getObject("end_date", LocalDate.class))
                    .createdAt(rs.getObject("created_at", LocalDateTime.class))
                    .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                    .build();
        }
    };

    public void save(Loan loan) {
        String sql = "INSERT INTO loans (user_id, account_id, loan_type, amount, interest_rate, term, start_date, end_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, loan.getUser().getId(), loan.getAccount().getId(), loan.getLoanType().name(), loan.getAmount(), loan.getInterestRate(), loan.getTerm(), loan.getStartDate(), loan.getEndDate(), loan.getCreatedAt(), loan.getUpdatedAt());
    }

    public Optional<Loan> findById(Long id, Long userId) {
        String sql = "SELECT * FROM loans WHERE id = ? AND user_id = ?";
        List<Loan> loans = jdbcTemplate.query(sql, LOAN_ROW_MAPPER, id, userId);
        return loans.stream().findFirst();
    }

    public List<Loan> findAll(Long userId) {
        String sql = "SELECT * FROM loans WHERE user_id = ?";
        return jdbcTemplate.query(sql, LOAN_ROW_MAPPER, userId);
    }

    public void update(Loan loan) {
        String sql = "UPDATE loans SET loan_type = ?, amount = ?, interest_rate = ?, term = ?, start_date = ?, end_date = ?, updated_at = ? WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, loan.getLoanType().name(), loan.getAmount(), loan.getInterestRate(), loan.getTerm(), loan.getStartDate(), loan.getEndDate(), loan.getUpdatedAt(), loan.getId(), loan.getUser().getId());
    }

    public void deleteById(Long id, Long userId) {
        String sql = "DELETE FROM loans WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }
}
