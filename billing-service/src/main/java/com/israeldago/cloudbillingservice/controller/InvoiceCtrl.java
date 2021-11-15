package com.israeldago.cloudbillingservice.controller;

import com.israeldago.cloudbillingservice.domain.dto.InvoiceResponseDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceRequestDTO;
import com.israeldago.cloudbillingservice.exceptions.CustomerNotFoundException;
import com.israeldago.cloudbillingservice.exceptions.InvoiceNotFoundException;
import com.israeldago.cloudbillingservice.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoiceCtrl {

    private final InvoiceService service;

    @GetMapping
    public List<InvoiceResponseDTO> getAllInvoices(
            @RequestParam(value = "customer", required = false) String customerId) {
        return ofNullable(customerId)
                .map(cId -> service.getCustomerInvoices(UUID.fromString(cId)))
                .orElseGet(service::getAllInvoices);
    }

    @GetMapping("{invoiceId}")
    public InvoiceResponseDTO getInvoice(@PathVariable UUID invoiceId) {
        return service.getInvoice(invoiceId);
    }

    @PostMapping
    public InvoiceResponseDTO saveInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        return service.saveInvoice(requestDTO);
    }

    @DeleteMapping("{invoiceId}")
    public ResponseEntity<?> delete(@PathVariable UUID invoiceId) {
        service.delete(invoiceId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({CustomerNotFoundException.class, InvoiceNotFoundException.class})
    ResponseEntity<String> onNotFoundException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
