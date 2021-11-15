package com.israeldago.cloudbillingservice.domain.dto;

import com.israeldago.cloudbillingservice.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequestDTO {
    private UUID customerId;
    private BigDecimal amount;
    private Currency currency;
}
