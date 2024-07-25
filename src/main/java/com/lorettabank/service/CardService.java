package com.lorettabank.service;

import com.lorettabank.dto.CardDTO;
import com.lorettabank.entity.Card;
import com.lorettabank.entity.User;
import com.lorettabank.entity.Account;
import com.lorettabank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public CardDTO createCard(CardDTO cardDTO) {
        Card card = Card.builder()
                .user(User.builder().id(cardDTO.getUserId()).build())  // Ensure User ID is a String
                .account(Account.builder().id(cardDTO.getAccountId()).build())  // Ensure Account ID is Integer
                .cardNumber(cardDTO.getCardNumber())
                .expiryDate(cardDTO.getExpiryDate())
                .cvv(cardDTO.getCvv())
                .creditLimit(cardDTO.getCreditLimit())
                .balance(cardDTO.getBalance())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        cardRepository.save(card);
        return cardDTO;
    }

    public CardDTO getCard(Integer id, String userId) {
        Optional<Card> cardOpt = cardRepository.findById(id, userId);
        if (cardOpt.isEmpty()) {
            throw new RuntimeException("Card not found");
        }
        Card card = cardOpt.get();
        return CardDTO.builder()
                .id(card.getId())
                .userId(card.getUser().getId())
                .accountId(card.getAccount().getId())
                .cardNumber(card.getCardNumber())
                .expiryDate(card.getExpiryDate())
                .cvv(card.getCvv())
                .creditLimit(card.getCreditLimit())
                .balance(card.getBalance())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .build();
    }

    public List<CardDTO> getCards(String userId) {
        List<Card> cards = cardRepository.findAll(userId);
        return cards.stream()
                .map(card -> CardDTO.builder()
                        .id(card.getId())
                        .userId(card.getUser().getId())
                        .accountId(card.getAccount().getId())
                        .cardNumber(card.getCardNumber())
                        .expiryDate(card.getExpiryDate())
                        .cvv(card.getCvv())
                        .creditLimit(card.getCreditLimit())
                        .balance(card.getBalance())
                        .createdAt(card.getCreatedAt())
                        .updatedAt(card.getUpdatedAt())
                        .build())
                .toList();
    }

    public CardDTO updateCard(Integer id, CardDTO cardDTO, String userId) {
        Optional<Card> cardOpt = cardRepository.findById(id, userId);
        if (cardOpt.isEmpty()) {
            throw new RuntimeException("Card not found");
        }
        Card card = cardOpt.get();
        card.setCardNumber(cardDTO.getCardNumber());
        card.setExpiryDate(cardDTO.getExpiryDate());
        card.setCvv(cardDTO.getCvv());
        card.setCreditLimit(cardDTO.getCreditLimit());
        card.setBalance(cardDTO.getBalance());
        card.setUpdatedAt(LocalDateTime.now());
        cardRepository.update(card);
        return cardDTO;
    }

    public void deleteCard(Integer id, String userId) {
        cardRepository.deleteById(id, userId);
    }
}
