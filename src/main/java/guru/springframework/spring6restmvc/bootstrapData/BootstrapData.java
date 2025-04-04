package guru.springframework.spring6restmvc.bootstrapData;

import com.github.javafaker.Faker;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {
        if(customerRepository.count() == 0) {
            List<Customer> customerList = Stream.generate(() -> Customer.builder()
                            .customerName(faker.name().fullName())
                            .lastModifiedDate(LocalDateTime.now())
                            .createdDate(LocalDateTime.now())
                            .build())
                    .limit(3)
                    .toList();

            customerRepository.saveAll(customerList);
        }
    }

    private void loadBeerData() {
        if(beerRepository.count() == 0) {
            List<Beer> beerList = Stream.generate(() -> Beer.builder()
                            .beerName(faker.beer().name())
                            .beerStyle(BeerStyle.values()[new Random().nextInt(BeerStyle.values().length)])
                            .upc(String.valueOf(faker.number().numberBetween(1000, 10000)))
                            .price(new BigDecimal(faker.commerce().price()))
                            .quantityOnHand(faker.number().numberBetween(100, 1000))
                            .createdDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build())
                    .limit(3)
                    .toList();

            beerRepository.saveAll(beerList);
        }
    }
}
