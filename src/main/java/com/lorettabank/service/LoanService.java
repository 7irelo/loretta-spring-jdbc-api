package com.lorettabank.service;

import com.lorettabank.dto.LoanDTO;
import com.lorettabank.entity.Loan;
import com.lorettabank.entity.LoanType;
import com.lorettabank.entity.User;
import com.lorettabank.entity.Account;
import com.lorettabank.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public LoanDTO createLoan(LoanDTO loanDTO) {
        Loan loan = Loan.builder()
                .user(new User(loanDTO.getUserId()))
                .account(new Account(loanDTO.getAccountId()))
                .loanType(loanDTO.getLoanType())
                .amount(loanDTO.getAmount())
                .interestRate(loanDTO.getInterestRate())
                .term(loanDTO.getTerm())
                .startDate(loanDTO.getStartDate())
                .endDate(loanDTO.getEndDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        loanRepository.save(loan);
        return loanDTO;
    }

    public LoanDTO getLoan(Long id, Long userId) {
        Optional<Loan> loanOpt = loanRepository.findById(id, userId);
        if (loanOpt.isEmpty()) {
            throw new RuntimeException("Loan not found");
        }
        Loan loan = loanOpt.get();
        return LoanDTO.builder()
                .id(loan.getId())
                .userId(loan.getUser().getId())
                .accountId(loan.getAccount().getId())
                .loanType(loan.getLoanType())
                .amount(loan.getAmount())
                .interestRate(loan.getInterestRate())
                .term(loan.getTerm())
                .startDate(loan.getStartDate())
                .endDate(loan.getEndDate())
                .createdAt(loan.getCreatedAt())
                .updatedAt(loan.getUpdatedAt())
                .build();
    }

    public List<LoanDTO> getLoans(Long userId) {
        List<Loan> loans = loanRepository.findAll(userId);
        return loans.stream()
                .map(loan -> LoanDTO.builder()
                        .id(loan.getId())
                        .userId(loan.getUser().getId())
                        .accountId(loan.getAccount().getId())
                        .loanType(loan.getLoanType())
                        .amount(loan.getAmount())
                        .interestRate(loan.getInterestRate())
                        .term(loan.getTerm())
                        .startDate(loan.getStartDate())
                        .endDate(loan.getEndDate())
                        .createdAt(loan.getCreatedAt())
                        .updatedAt(loan.getUpdatedAt())
                        .build())
                .toList();
    }

    public LoanDTO updateLoan(Long id, LoanDTO loanDTO, Long userId) {
        Optional<Loan> loanOpt = loanRepository.findById(id, userId);
        if (loanOpt.isEmpty()) {
            throw new RuntimeException("Loan not found");
        }
        Loan loan = loanOpt.get();
        loan.setLoanType(loanDTO.getLoanType());
        loan.setAmount(loanDTO.getAmount());
        loan.setInterestRate(loanDTO.getInterestRate());
        loan.setTerm(loanDTO.getTerm());
        loan.setStartDate(loanDTO.getStartDate());
        loan.setEndDate(loanDTO.getEndDate());
        loan.setUpdatedAt(LocalDateTime.now());
        loanRepository.update(loan);
        return loanDTO;
    }

    public void deleteLoan(Long id, Long userId) {
        loanRepository.deleteById(id, userId);
    }
}
