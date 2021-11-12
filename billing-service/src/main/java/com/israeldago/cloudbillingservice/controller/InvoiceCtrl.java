package com.israeldago.cloudbillingservice.controller;

import com.israeldago.cloudbillingservice.domain.dto.InvoiceFullResponseDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceRequestDTO;
import com.israeldago.cloudbillingservice.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class InvoiceCtrl {

    private final InvoiceService service;

    @GetMapping("/invoices")
    public List<InvoiceFullResponseDTO> getAllInvoices(
            @RequestParam(value = "customer", required = false) String customerId) {
        return ofNullable(customerId)
                .map(cId -> service.getCustomerInvoices(UUID.fromString(cId)))
                .orElseGet(service::getAllInvoices);
    }

    @GetMapping("/invoices/{invoiceId}")
    public InvoiceFullResponseDTO getInvoice(@PathVariable String invoiceId) {
        return service.getInvoice(UUID.fromString(invoiceId));
    }

    @PostMapping("/invoices")
    public InvoiceFullResponseDTO saveInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        return service.saveInvoice(requestDTO);
    }

}
