package guru.springframework.spring6reactive.service.impl;

import guru.springframework.spring6reactive.mapper.BeerMapper;
import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.repository.BeerRepository;
import guru.springframework.spring6reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDTO) {
        return this.beerRepository.save(this.beerMapper.toDomain(beerDTO))
                .map(this.beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer beerId) {
        return this.beerRepository.findById(beerId)
                .map(this.beerMapper::toDTO);
    }

    @Override
    public Flux<BeerDTO> listBeer() {
        return this.beerRepository.findAll()
                .map(this.beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO) {
        return this.beerRepository.findById(beerId)
                .map(beerToUpdate -> this.beerMapper.update(beerToUpdate, beerDTO))
                .flatMap(this.beerRepository::save)
                .map(this.beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> patchBeer(final Integer beerId, BeerDTO beerDTO) {
        return this.beerRepository.findById(beerId)
                .map(beerToUpdate -> this.beerMapper.merge(beerToUpdate, beerDTO))
                .flatMap(this.beerRepository::save)
                .map(this.beerMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteById(Integer beerId) {
        return this.beerRepository.deleteById(beerId);
    }
}
