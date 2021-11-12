package com.israeldago.cloudbillingservice.service;

import com.israeldago.cloudbillingservice.domain.dto.InvoiceFullResponseDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceRequestDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceResponseDTO;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    InvoiceFullResponseDTO saveInvoice(InvoiceRequestDTO requestDTO);
    InvoiceFullResponseDTO getInvoice(UUID invoiceId);
    List<InvoiceFullResponseDTO> getAllInvoices();
    List<InvoiceFullResponseDTO> getCustomerInvoices(UUID customerId);
}
