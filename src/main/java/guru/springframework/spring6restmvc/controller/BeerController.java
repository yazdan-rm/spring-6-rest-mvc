package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @PostMapping
    public ResponseEntity<Beer> handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + savedBeer.getId().toString());
        headers.add("ZoneID", HttpHeaders.encodeBasicAuth("test", "test123", Charset.defaultCharset()));

        return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    @GetMapping("{beerId}")
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get Beer Id - in controller -123");
        Beer beerById = beerService.getBeerById(beerId);
        System.out.println(beerById);
        return beerById;
    }
}