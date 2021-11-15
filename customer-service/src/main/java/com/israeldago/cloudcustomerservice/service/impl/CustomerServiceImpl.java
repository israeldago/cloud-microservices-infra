package com.israeldago.cloudcustomerservice.service.impl;

import com.israeldago.cloudcustomerservice.domain.dto.CustomerDTO;
import com.israeldago.cloudcustomerservice.domain.entities.Customer;
import com.israeldago.cloudcustomerservice.exceptions.CustomerNotFoundException;
import com.israeldago.cloudcustomerservice.mapper.CustomerMapper;
import com.israeldago.cloudcustomerservice.repositories.CustomerRepository;
import com.israeldago.cloudcustomerservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.israeldago.cloudcustomerservice.service.CustomerService.assignValueWithReflection;

@Service
@Transactional
@AllArgsConstructor
class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        final var customer = mapper.fromDTO(customerDTO);
        customer.setId(UUID.randomUUID());
        final var saved = repository.save(customer);
        return mapper.toDTO(saved);
    }

    @Override
    public CustomerDTO getByCustomerId(UUID customerId) {
        return repository.findById(customerId)
                         .map(mapper::toDTO)
                         .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public CustomerDTO update(UUID customerId, CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return repository.findById(customerDTO.getId())
                         .map(c -> mapper.fromDTO(customerDTO))
                         .map(repository::save)
                         .map(mapper::toDTO)
                         .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public CustomerDTO patch(UUID customerId, Map<String, String> customerDetails) {
        return repository.findById(customerId)
                         .map(customerWithGivenPatch(customerDetails))
                         .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public void delete(UUID customerId) {
        final var found = getByCustomerId(customerId);
        repository.delete(mapper.fromDTO(found));
    }

    @Override
    public List<CustomerDTO> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toDTO)
                         .collect(Collectors.toList());
    }

    private Function<Customer, CustomerDTO> customerWithGivenPatch(Map<String, String> customerDetails) {
        return customer -> {
            customerDetails.forEach((k, v) -> assignValueWithReflection(k, v, customer));
            repository.save(customer);
            return mapper.toDTO(customer);
        };
    }
}
