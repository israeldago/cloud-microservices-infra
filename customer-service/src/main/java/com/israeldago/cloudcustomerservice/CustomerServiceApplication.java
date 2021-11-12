package com.israeldago.cloudcustomerservice;

import com.israeldago.cloudcustomerservice.domain.dto.CustomerDTO;
import com.israeldago.cloudcustomerservice.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

import static java.util.UUID.fromString;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService) {
        return args -> Stream.of(new CustomerDTO(fromString("e048a7c2-4707-4a87-9b44-34070b061eb6"), "Teo", "teo@gmail.com"),
                                 new CustomerDTO(fromString("090116da-fb6b-4b4f-b0b3-2984ccacda4a"), "Marc", "marc@gmail.com"),
                                 new CustomerDTO(fromString("7e3d781e-5aa6-4ebc-aa74-5bedccabd3f3"), "Micheline", "micheline@gmail.com"))
                             .map(customerService::save)
                             .forEach(System.out::println);
    }
}
