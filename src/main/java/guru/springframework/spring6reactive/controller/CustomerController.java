package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private static final String CUSTOMER_PATH = "/api/v2/customers";

    private static final String CUSTOMER_ID_PATH = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;


    @GetMapping(CUSTOMER_PATH)
    public Flux<CustomerDTO> listCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_ID_PATH)
    public Mono<CustomerDTO> getCustomerById(@PathVariable Integer customerId) {
        return this.customerService.getById(customerId);
    }

    @PostMapping(CUSTOMER_PATH)
    public Mono<ResponseEntity<Void>> createCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        return this.customerService.createCustomer(customerDTO)
                .map(savedCustomer -> ResponseEntity.created(this.buildUri(savedCustomer.getId())).build());
    }

    @PutMapping(CUSTOMER_ID_PATH)
    public Mono<ResponseEntity<Void>> updateCustomer(@PathVariable Integer customerId,
                                                     @Validated @RequestBody CustomerDTO customerDTO) {
        return this.customerService.updateCustomer(customerId, customerDTO)
                .map(updatedCustomer -> ResponseEntity.ok().build());
    }

    @PatchMapping(CUSTOMER_ID_PATH)
    public Mono<ResponseEntity<Void>> patchCustomer(@PathVariable Integer customerId,
                                                    @Validated @RequestBody CustomerDTO customerDTO) {
        return this.customerService.patchCustomer(customerId, customerDTO)
                .map(patchedCustomer -> ResponseEntity.ok().build());
    }

    @DeleteMapping(CUSTOMER_ID_PATH)
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer customerId) {
        return this.customerService.deleteCustomer(customerId)
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }

    private URI buildUri(final Integer customerId) {
        return UriComponentsBuilder.fromUriString(
                        "http://localhost:8080" + CUSTOMER_PATH + "/" + customerId)
                .build().toUri();
    }
}
