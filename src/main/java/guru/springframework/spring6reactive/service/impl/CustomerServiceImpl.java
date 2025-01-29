package guru.springframework.spring6reactive.service.impl;

import guru.springframework.spring6reactive.mapper.CustomerMapper;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.repository.CustomerRepository;
import guru.springframework.spring6reactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;
    @Override
    public Flux<CustomerDTO> getAllCustomers() {
        return this.customerRepository.findAll()
                .map(this.customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDTO> getById(Integer customerId) {
        return this.customerRepository.findById(customerId)
                .map(this.customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO) {
        return this.customerRepository.save(this.customerMapper.toDomain(customerDTO)).map(this.customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(final Integer customerId, final CustomerDTO customerDTO) {
        return this.customerRepository.findById(customerId)
                .map(toUpdate -> this.customerMapper.update(toUpdate, customerDTO))
                .flatMap(this.customerRepository::save)
                .map(this.customerMapper::toDto);

    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
        return this.customerRepository.findById(customerId)
                .map(customerToPatch -> this.customerMapper.merge(customerToPatch, customerDTO))
                .flatMap(this.customerRepository::save)
                .map(this.customerMapper::toDto);
    }

    @Override
    public Mono<Void> deleteCustomer(Integer customerId) {
        return this.customerRepository.deleteById(customerId);
    }


}
