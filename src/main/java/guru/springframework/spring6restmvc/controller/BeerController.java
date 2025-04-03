package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.constant.RestConstant;
import guru.springframework.spring6restmvc.exception.NotFoundException;
import guru.springframework.spring6restmvc.model.BeerDTO;
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
@RequestMapping(RestConstant.BEER_URL)
public class BeerController {

    private final BeerService beerService;

    @PatchMapping("{beerId}")
    public ResponseEntity<BeerDTO> updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity<BeerDTO> deleteById(@PathVariable UUID beerId) {
        beerService.deleteById(beerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{beerId}")
    public ResponseEntity<BeerDTO> updateById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        beerService.updateBeerById(beerId, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<BeerDTO> handlePost(@RequestBody BeerDTO beer){
        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + savedBeer.getId().toString());
        headers.add("ZoneID", HttpHeaders.encodeBasicAuth("test", "test123", Charset.defaultCharset()));

        return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @GetMapping("{beerId}")
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get BeerDTO Id - in controller -123");
        BeerDTO beerById = beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
        System.out.println(beerById);
        return beerById;
    }
}