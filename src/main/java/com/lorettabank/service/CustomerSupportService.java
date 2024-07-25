package com.lorettabank.service;

import com.lorettabank.dto.CustomerSupportDTO;
import com.lorettabank.entity.CustomerSupport;
import com.lorettabank.entity.SupportStatus;
import com.lorettabank.entity.User;
import com.lorettabank.repository.CustomerSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerSupportService {

    @Autowired
    private CustomerSupportRepository customerSupportRepository;

    public CustomerSupportDTO createSupport(CustomerSupportDTO supportDTO) {
        CustomerSupport customerSupport = CustomerSupport.builder()
                .user(new User(supportDTO.getUserId()))
                .query(supportDTO.getQuery())
                .response(supportDTO.getResponse())
                .status(supportDTO.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        customerSupportRepository.save(customerSupport);
        return supportDTO;
    }

    public CustomerSupportDTO getSupport(Integer id, Long userId) {
        Optional<CustomerSupport> supportOpt = customerSupportRepository.findById(id, userId);
        if (supportOpt.isEmpty()) {
            throw new RuntimeException("Support query not found");
        }
        CustomerSupport support = supportOpt.get();
        return CustomerSupportDTO.builder()
                .id(support.getId())
                .userId(support.getUser().getId())
                .query(support.getQuery())
                .response(support.getResponse())
                .status(support.getStatus())
                .createdAt(support.getCreatedAt())
                .updatedAt(support.getUpdatedAt())
                .user(new UserDTO(support.getUser().getId())) // Assuming you have a constructor or method to convert User to UserDTO
                .build();
    }

    public List<CustomerSupportDTO> getSupports(Long userId) {
        List<CustomerSupport> supports = customerSupportRepository.findAll(userId);
        return supports.stream()
                .map(support -> CustomerSupportDTO.builder()
                        .id(support.getId())
                        .userId(support.getUser().getId())
                        .query(support.getQuery())
                        .response(support.getResponse())
                        .status(support.getStatus())
                        .createdAt(support.getCreatedAt())
                        .updatedAt(support.getUpdatedAt())
                        .user(new UserDTO(support.getUser().getId())) // Assuming you have a constructor or method to convert User to UserDTO
                        .build())
                .toList();
    }

    public CustomerSupportDTO updateSupport(Integer id, CustomerSupportDTO supportDTO, Long userId) {
        Optional<CustomerSupport> supportOpt = customerSupportRepository.findById(id, userId);
        if (supportOpt.isEmpty()) {
            throw new RuntimeException("Support query not found");
        }
        CustomerSupport support = supportOpt.get();
        support.setQuery(supportDTO.getQuery());
        support.setResponse(supportDTO.getResponse());
        support.setStatus(supportDTO.getStatus());
        support.setUpdatedAt(LocalDateTime.now());
        customerSupportRepository.update(support);
        return supportDTO;
    }

    public void deleteSupport(Integer id, Long userId) {
        customerSupportRepository.deleteById(id, userId);
    }
}
