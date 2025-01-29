package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.model.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    WebTestClient client;

    @Test
    @Order(1)
    void shouldListCustomers() {
        this.client.get()
                .uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void shouldGetCustomerById() {
        this.client.get()
                .uri(CustomerController.CUSTOMER_ID_PATH, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerDTO.class);
    }

    @Test
    void shouldCreatCustomer() {
        this.client.post()
                .uri(CustomerController.CUSTOMER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(this.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customers/4");
    }

    @Test
    void shouldUpdateCustomer() {
        this.client.put()
                .uri(CustomerController.CUSTOMER_ID_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(this.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(5)
    void shouldDeleteCustomer() {
        this.client.delete()
                .uri(CustomerController.CUSTOMER_ID_PATH,1)
                .exchange()
                .expectStatus().isNoContent();
    }

    private CustomerDTO getTestCustomer() {
        return CustomerDTO.builder()
                .userName("Test name")
                .build();
    }
}