package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.exception.NotFoundException;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Test
    void testGetCustomerById(){
        Customer customer = customerRepository.findAll().getFirst();

        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(customer.getId()));

    }

    @Test
    void testListCustomer(){
        List<CustomerDTO> customers = customerController.getCustomers();
        assertThat(customers.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyListCustomers(){
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOS = customerController.getCustomers();
        assertThat(customerDTOS.size()).isEqualTo(0);
    }

    @Test
    void testCustomerIdNotFound(){
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void saveCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("TEST YAZDAN")
                .build();

        ResponseEntity<CustomerDTO> responseEntity = customerController.handlePostRequest(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders()).isNotNull();

        String[] locationUUID = Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath().split("/");
        UUID saveUUID = UUID.fromString(locationUUID[locationUUID.length-1]);

        Customer customer = customerRepository.findById(saveUUID).get();
        assertThat(customer.getId()).isNotNull();
    }
}