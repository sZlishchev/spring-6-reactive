package guru.springframework.spring6reactive.service;

import guru.springframework.spring6reactive.model.BeerDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Flux<BeerDTO> listBeer();

    Mono<BeerDTO> getBeerById(Integer beerId);

    Mono<BeerDTO> createBeer(BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO);

    Mono<Void> deleteById(Integer beerId);
}
