package com.lorettabank.controller;

import com.lorettabank.dto.CardDTO;
import com.lorettabank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO, @RequestAttribute Long userId) {
        try {
            cardDTO.setUserId(userId);
            CardDTO createdCard = cardService.createCard(cardDTO);
            return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CardDTO> getCard(@PathVariable Integer id, @RequestAttribute Long userId) {
        try {
            CardDTO cardDTO = cardService.getCard(id, userId);
            return ResponseEntity.ok(cardDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CardDTO>> getCards(@RequestAttribute Long userId) {
        try {
            List<CardDTO> cards = cardService.getCards(userId);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CardDTO> updateCard(@PathVariable Integer id, @RequestBody CardDTO cardDTO, @RequestAttribute Long userId) {
        try {
            CardDTO updatedCard = cardService.updateCard(id, cardDTO, userId);
            return ResponseEntity.ok(updatedCard);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteCard(@PathVariable Integer id, @RequestAttribute Long userId) {
        try {
            cardService.deleteCard(id, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
