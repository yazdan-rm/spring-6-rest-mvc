package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;
}
