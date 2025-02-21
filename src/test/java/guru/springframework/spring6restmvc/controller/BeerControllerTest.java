package guru.springframework.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class BeerControllerTest {
    
    @Autowired
    private BeerController controller;

    @Test
    void getBeerById() {
        System.out.println(controller.getBeerById(UUID.randomUUID()));
    }
}