package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class BeerController {
    public static final String BEER_PATH = "/api/v2/beer";

    public static final String BEER_ID_PATH = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PostMapping(BEER_PATH)
    public Mono<ResponseEntity<Void>> createNewBeer(@RequestBody BeerDTO beerDTO) {
       return this.beerService.createBeer(beerDTO)
               .map(savedBeer ->
               ResponseEntity.created(this.buildUri(beerDTO.getId()))
                       .build());
    }

    @GetMapping(BEER_ID_PATH)
    public Mono<BeerDTO> beerById(@PathVariable Integer beerId) {
        return this.beerService.getBeerById(beerId);
    }

    @GetMapping(BEER_PATH)
    public Flux<BeerDTO> listBeer(){
        return this.beerService.listBeer();
    }

    @PatchMapping(BEER_ID_PATH)
    public ResponseEntity<Void> updateBeer(@PathVariable Integer beerId,
                                                 @RequestBody BeerDTO beerDTO) {

         this.beerService.updateBeer(beerId, beerDTO).subscribe();

         return ResponseEntity.ok().build();
    }

    private URI buildUri(final Integer beerId) {
        return UriComponentsBuilder.fromUriString(
                                        "http://localhost:8080" + BEER_PATH + "/" + beerId)
                                .build().toUri();
    }
}
