package com.israeldago.cloudbillingservice.service;

import com.israeldago.cloudbillingservice.domain.dto.InvoiceResponseDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceRequestDTO;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    InvoiceResponseDTO saveInvoice(InvoiceRequestDTO requestDTO);
    InvoiceResponseDTO getInvoice(UUID invoiceId);
    List<InvoiceResponseDTO> getAllInvoices();
    List<InvoiceResponseDTO> getCustomerInvoices(UUID customerId);
    void delete(UUID invoiceId);
}
