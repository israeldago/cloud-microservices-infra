package com.israeldago.cloudcustomerservice.mapper;

import com.israeldago.cloudcustomerservice.domain.dto.CustomerDTO;
import com.israeldago.cloudcustomerservice.domain.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer fromDTO(CustomerDTO customerDTO);
    CustomerDTO toDTO(Customer customer);
}
