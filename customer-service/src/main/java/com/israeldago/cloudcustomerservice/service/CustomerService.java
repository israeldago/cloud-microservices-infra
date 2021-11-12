package com.israeldago.cloudcustomerservice.service;

import com.israeldago.cloudcustomerservice.domain.dto.CustomerDTO;
import com.israeldago.cloudcustomerservice.domain.entities.Customer;
import lombok.SneakyThrows;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public interface CustomerService {
    CustomerDTO save(CustomerDTO customerDTO);

    CustomerDTO getByCustomerId(UUID customerId);

    CustomerDTO update(UUID customerId, CustomerDTO customerDTO);

    CustomerDTO patch(UUID customerId, Map<String, String> customerDetails);

    List<CustomerDTO> findAll();

    @SneakyThrows
    static <T> void assignValueWithReflection(String fieldName, Object newValue, T target) {
        final var fieldOpt = ofNullable(ReflectionUtils.findField(Customer.class, fieldName));
        if (fieldOpt.isPresent()) {
            final var field = fieldOpt.get();
            field.setAccessible(true);
            field.set(target, newValue);
        }
    }
}
