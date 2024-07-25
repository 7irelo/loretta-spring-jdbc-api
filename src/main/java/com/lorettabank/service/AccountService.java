package com.lorettabank.service;

import com.lorettabank.dto.AccountDTO;
import com.lorettabank.entity.Account;
import com.lorettabank.entity.AccountType;
import com.lorettabank.entity.AccountStatus;
import com.lorettabank.entity.User;
import com.lorettabank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(AccountDTO accountDTO) {
        Account account = Account.builder()
                .accountNumber(accountDTO.getAccountNumber())
                .name(accountDTO.getName())
                .user(new User()) // Handle the user mapping as needed
                .accountType(AccountType.valueOf(accountDTO.getAccountType()))
                .availableBalance(accountDTO.getAvailableBalance())
                .latestBalance(accountDTO.getLatestBalance())
                .accountStatus(AccountStatus.valueOf(accountDTO.getAccountStatus()))
                .imageUrl(accountDTO.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        accountRepository.save(account);
    }

    public Optional<AccountDTO> getAccount(Integer id) {
        return accountRepository.findById(id)
                .map(this::mapToDTO);
    }

    public List<AccountDTO> getAccounts(String userId) {
        return accountRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void updateAccount(AccountDTO accountDTO) {
        Account account = Account.builder()
                .id(accountDTO.getId())
                .accountNumber(accountDTO.getAccountNumber())
                .name(accountDTO.getName())
                .user(new User()) // Handle the user mapping as needed
                .accountType(AccountType.valueOf(accountDTO.getAccountType()))
                .availableBalance(accountDTO.getAvailableBalance())
                .latestBalance(accountDTO.getLatestBalance())
                .accountStatus(AccountStatus.valueOf(accountDTO.getAccountStatus()))
                .imageUrl(accountDTO.getImageUrl())
                .updatedAt(LocalDateTime.now())
                .build();
        accountRepository.update(account);
    }

    public void deleteAccount(Integer id, String userId) {
        accountRepository.delete(id, userId);
    }

    private AccountDTO mapToDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .name(account.getName())
                .userId(account.getUser().getId())
                .accountType(account.getAccountType().name())
                .availableBalance(account.getAvailableBalance())
                .latestBalance(account.getLatestBalance())
                .accountStatus(account.getAccountStatus().name())
                .imageUrl(account.getImageUrl())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
