package guru.springframework.spring6restmvc.repository;

import com.github.javafaker.Faker;
import guru.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Faker faker = new Faker();

    @Test
    void testSaveCustomer() {

        Customer savedCustomer = customerRepository.save(
                Customer.builder()
                        .customerName(faker.funnyName().name())
                        .build()
        );

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
    }
}