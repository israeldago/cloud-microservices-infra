package com.israeldago.cloudbillingservice.mappers;

import com.israeldago.cloudbillingservice.domain.dto.InvoiceFullResponseDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceRequestDTO;
import com.israeldago.cloudbillingservice.domain.dto.InvoiceResponseDTO;
import com.israeldago.cloudbillingservice.domain.entities.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice fromRequestDTO(InvoiceRequestDTO requestDTO);
    InvoiceResponseDTO toResponseDTO(Invoice invoice);
    InvoiceFullResponseDTO toFullResponseDTO(Invoice invoice);
}
