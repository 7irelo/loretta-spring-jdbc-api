package com.lorettabank.controller;

import com.lorettabank.dto.CustomerSupportDTO;
import com.lorettabank.service.CustomerSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-support")
public class CustomerSupportController {

    @Autowired
    private CustomerSupportService customerSupportService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createSupport(@RequestBody CustomerSupportDTO supportDTO, @RequestAttribute Long userId) {
        try {
            supportDTO.setUserId(userId);
            CustomerSupportDTO createdSupport = customerSupportService.createSupport(supportDTO);
            return new ResponseEntity<>(createdSupport, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getSupport(@PathVariable Integer id, @RequestAttribute Long userId) {
        try {
            CustomerSupportDTO supportDTO = customerSupportService.getSupport(id, userId);
            return ResponseEntity.ok(supportDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getSupports(@RequestAttribute Long userId) {
        try {
            List<CustomerSupportDTO> supports = customerSupportService.getSupports(userId);
            return ResponseEntity.ok(supports);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateSupport(@PathVariable Integer id, @RequestBody CustomerSupportDTO supportDTO, @RequestAttribute Long userId) {
        try {
            CustomerSupportDTO updatedSupport = customerSupportService.updateSupport(id, supportDTO, userId);
            return ResponseEntity.ok(updatedSupport);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteSupport(@PathVariable Integer id, @RequestAttribute Long userId) {
        try {
            customerSupportService.deleteSupport(id, userId);
            return ResponseEntity.ok("Support query deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
