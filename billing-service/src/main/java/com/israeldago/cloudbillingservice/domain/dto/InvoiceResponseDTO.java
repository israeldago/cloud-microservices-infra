package com.israeldago.cloudbillingservice.domain.dto;

import com.israeldago.cloudbillingservice.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponseDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private Currency currency;
    private CustomerDTO customer;
}
