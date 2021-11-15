package com.israeldago.cloudbillingservice.service.impl;

import com.israeldago.cloudbillingservice.client.CustomerApiClient;
import com.israeldago.cloudbillingservice.domain.dto.CustomerDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceResponseDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceRequestDTO;
import com.israeldago.cloudbillingservice.domain.entities.Invoice;
import com.israeldago.cloudbillingservice.exceptions.CustomerNotFoundException;
import com.israeldago.cloudbillingservice.exceptions.InvoiceNotFoundException;
import com.israeldago.cloudbillingservice.mappers.InvoiceMapper;
import com.israeldago.cloudbillingservice.repository.InvoiceRepository;
import com.israeldago.cloudbillingservice.service.InvoiceService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public InvoiceResponseDTO saveInvoice(InvoiceRequestDTO requestDTO) {
        final var customer = retrieveCustomer(requestDTO.getCustomerId());

        final var invoice = mapper.fromRequestDTO(requestDTO);
        invoice.setId(UUID.randomUUID());
        invoice.setCreatedAt(LocalDateTime.now());

        final var saved = repository.save(invoice);
        final var responseDTO = mapper.toResponseDTO(saved);
        responseDTO.setCustomer(customer);
        return responseDTO;
    }

    @Override
    public InvoiceResponseDTO getInvoice(UUID invoiceId) {
        final var invoice = repository.findById(invoiceId).orElseThrow(InvoiceNotFoundException::new);
        final var customer = retrieveCustomer(invoice.getCustomerId());

        final var responseDTO = mapper.toResponseDTO(invoice);
        responseDTO.setCustomer(customer);
        return responseDTO;
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        return repository.findAll()
                         .stream()
                         .map(invoice -> {
                             final var customer = retrieveCustomer(invoice.getCustomerId());
                             final var responseDTO = mapper.toResponseDTO(invoice);
                             responseDTO.setCustomer(customer);
                             return responseDTO;
                         })
                         .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> getCustomerInvoices(UUID customerId) {
        final var customer = retrieveCustomer(customerId);

        return repository.findByCustomerId(customerId)
                         .stream()
                         .map(mapper::toResponseDTO)
                         .peek(responseDTO -> responseDTO.setCustomer(customer))
                         .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID invoiceId) {
        final var invoice = repository.findById(invoiceId)
                                      .orElseThrow(InvoiceNotFoundException::new);
        repository.delete(invoice);
    }


    private CustomerDTO retrieveCustomer(UUID customerId) {
        try {
            return customerApiClient.findCustomerById(customerId);
        } catch (FeignException e) {
            if (HttpStatus.NOT_FOUND.value() == e.status()) {
                throw new CustomerNotFoundException("Not found customer with id: " + customerId);
            }
            throw e;
        }
    }
}
