package com.israeldago.cloudbillingservice.service.impl;

import com.israeldago.cloudbillingservice.client.CustomerApiClient;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceFullResponseDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceRequestDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceResponseDTO;
import com.israeldago.cloudbillingservice.exceptions.CustomerNotFoundException;
import com.israeldago.cloudbillingservice.exceptions.InvoiceNotFoundException;
import com.israeldago.cloudbillingservice.mappers.InvoiceMapper;
import com.israeldago.cloudbillingservice.repository.InvoiceRepository;
import com.israeldago.cloudbillingservice.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;
    private final CustomerApiClient customerApiClient;
    private final InvoiceMapper mapper;

    @Override
    public InvoiceFullResponseDTO saveInvoice(InvoiceRequestDTO requestDTO) {
        final var customer =
                customerApiClient.findCustomerById(requestDTO.getCustomerId())
                                 .orElseThrow(CustomerNotFoundException::new);

        final var invoice = mapper.fromRequestDTO(requestDTO);
        invoice.setId(UUID.randomUUID());
        invoice.setCreatedAt(LocalDateTime.now());

        final var saved = repository.save(invoice);
        final var responseDTO = mapper.toFullResponseDTO(saved);
        responseDTO.setCustomer(customer);
        return responseDTO;
    }

    @Override
    public InvoiceFullResponseDTO getInvoice(UUID invoiceId) {
        final var invoice = repository.findById(invoiceId).orElseThrow(InvoiceNotFoundException::new);
        final var customer =
                customerApiClient.findCustomerById(invoice.getCustomerId())
                                 .orElseThrow(CustomerNotFoundException::new);

        final var responseDTO = mapper.toFullResponseDTO(invoice);
        responseDTO.setCustomer(customer);
        return responseDTO;
    }

    @Override
    public List<InvoiceFullResponseDTO> getAllInvoices() {
        return repository.findAll()
                         .stream()
                         .map(invoice -> {
                             final var customer =
                                     customerApiClient.findCustomerById(invoice.getCustomerId())
                                                      .orElseThrow(CustomerNotFoundException::new);
                             final var responseDTO = mapper.toFullResponseDTO(invoice);
                             responseDTO.setCustomer(customer);
                             return responseDTO;
                         })
                         .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceFullResponseDTO> getCustomerInvoices(UUID customerId) {
        final var customer =
                customerApiClient.findCustomerById(customerId)
                                 .orElseThrow(CustomerNotFoundException::new);

        return repository.findByCustomerId(customerId)
                         .stream()
                         .map(mapper::toFullResponseDTO)
                         .peek(responseDTO -> responseDTO.setCustomer(customer))
                         .collect(Collectors.toList());
    }

}
