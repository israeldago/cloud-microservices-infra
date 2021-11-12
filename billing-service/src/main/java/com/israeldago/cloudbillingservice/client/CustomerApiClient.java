package com.israeldago.cloudbillingservice.client;

import com.israeldago.cloudbillingservice.domain.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerApiClient {

    @GetMapping("/api/customers/{customerId}")
    Optional<CustomerDTO> findCustomerById(@PathVariable UUID customerId);

    @GetMapping("/api/customers/{customerId}")
    List<CustomerDTO> findAllCustomers();
}
