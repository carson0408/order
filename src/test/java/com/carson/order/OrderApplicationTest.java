package com.carson.order;

import com.carson.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * ClassName OrderApplicationTest
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTest {

    private WebTestClient client;

    @Before
    public void init() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:8081/flux/routers/orders/10").build();
    }

    @Test
    public void testGetAll() {
        this.client.get().uri("/")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Order.class);
    }



}
