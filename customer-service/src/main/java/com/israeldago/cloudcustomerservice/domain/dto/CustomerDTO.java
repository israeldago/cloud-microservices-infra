package com.israeldago.cloudcustomerservice.domain.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    UUID id;
    String name;
    String email;
}
