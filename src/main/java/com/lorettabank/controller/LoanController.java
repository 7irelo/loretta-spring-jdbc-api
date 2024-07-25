package com.lorettabank.controller;

import com.lorettabank.dto.LoanDTO;
import com.lorettabank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createLoan(@RequestBody LoanDTO loanDTO, @RequestAttribute Long userId) {
        try {
            LoanDTO createdLoan = loanService.createLoan(loanDTO);
            return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getLoan(@PathVariable Long id, @RequestAttribute Long userId) {
        try {
            LoanDTO loanDTO = loanService.getLoan(id, userId);
            return ResponseEntity.ok(loanDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getLoans(@RequestAttribute Long userId) {
        try {
            List<LoanDTO> loans = loanService.getLoans(userId);
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateLoan(@PathVariable Long id, @RequestBody LoanDTO loanDTO, @RequestAttribute Long userId) {
        try {
            LoanDTO updatedLoan = loanService.updateLoan(id, loanDTO, userId);
            return ResponseEntity.ok(updatedLoan);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteLoan(@PathVariable Long id, @RequestAttribute Long userId) {
        try {
            loanService.deleteLoan(id, userId);
            return ResponseEntity.ok("Loan deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
