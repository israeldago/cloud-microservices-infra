package com.israeldago.cloudbillingservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceFullResponseDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private CustomerDTO customer;
}
