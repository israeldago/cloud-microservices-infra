package com.israeldago.cloudcustomerservice.controller;

import com.israeldago.cloudcustomerservice.domain.dto.CustomerDTO;
import com.israeldago.cloudcustomerservice.exceptions.CustomerNotFoundException;
import com.israeldago.cloudcustomerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerCtrl {
    private final CustomerService service;

    @GetMapping
    List<CustomerDTO> getAllCustomers() {
        return service.findAll();
    }

    @GetMapping("/{customerId}")
    CustomerDTO getByCustomerId(@PathVariable String customerId) {
        return service.getByCustomerId(UUID.fromString(customerId));
    }

    @PostMapping
    CustomerDTO save(@RequestBody CustomerDTO customerDTO) {
        return service.save(customerDTO);
    }

    @PutMapping("{customerId}")
    CustomerDTO update(@PathVariable String customerId, @RequestBody CustomerDTO customerDTO) {
        return service.update(UUID.fromString(customerId), customerDTO);
    }

    @PatchMapping("{customerId}")
    CustomerDTO patch(@PathVariable String customerId, @RequestBody Map<String, String> customerDetails) {
        return service.patch(UUID.fromString(customerId), customerDetails);
    }


    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity<String> onCustomerNotFoundException(Exception e) {
//        return ResponseEntity.notFound().header("Root-Cause", e.getMessage()).build();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
