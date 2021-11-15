package com.israeldago.cloudbillingservice.domain.entities;

import com.israeldago.cloudbillingservice.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    private UUID id;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    private UUID customerId;
}
