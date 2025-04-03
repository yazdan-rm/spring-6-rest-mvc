package guru.springframework.spring6restmvc.repository;

import com.github.javafaker.Faker;
import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    Faker faker = new Faker();

    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(
                Beer.builder()
                        .beerName(faker.beer().name())
                        .build()
        );

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}