package com.lorettabank.controller;

import com.lorettabank.dto.AccountDTO;
import com.lorettabank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountDTO accountDTO) {
        accountService.createAccount(accountDTO);
        return ResponseEntity.status(201).body("Account created successfully");
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam String userId) {
        List<AccountDTO> accounts = accountService.getAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Integer id) {
        return accountService.getAccount(id)
                .map(accountDTO -> ResponseEntity.ok(accountDTO))
                .orElseGet(() -> ResponseEntity.status(404).build()); // Return 404 with no body
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Integer id, @RequestBody AccountDTO accountDTO) {
        accountDTO.setId(id);
        accountService.updateAccount(accountDTO);
        return ResponseEntity.ok("Account updated successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchAccount(@PathVariable Integer id, @RequestBody AccountDTO accountDTO) {
        accountDTO.setId(id);
        accountService.updateAccount(accountDTO);
        return ResponseEntity.ok("Account updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer id, @RequestParam String userId) {
        accountService.deleteAccount(id, userId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
