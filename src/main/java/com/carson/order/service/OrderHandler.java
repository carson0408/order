package com.carson.order.service;

import com.carson.order.cache.CacheManager;
import com.carson.order.domain.*;
import com.carson.order.repository.OrderRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * ClassName OrderHandler
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Component
public class OrderHandler {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private CacheManager cacheManager;

    public Mono<ServerResponse> orders(ServerRequest request){
        Mono<OrderItem> orderItemMono = request.bodyToMono(OrderItem.class);
        OrderItem orderItem = orderItemMono.block();
        String name = String.valueOf(request.attribute("name").orElse(""));
        String street = String.valueOf(request.attribute("street").orElse(""));
        String zip = String.valueOf(request.attribute("zip").orElse(""));
        Collection<OrderItem> orderItems = new ArrayList<>();
        ((ArrayList<OrderItem>) orderItems).add(orderItem);
        Order order = Order.builder().withName(name).withAddress(createAddress(street, zip)).withItems(orderItems).build();
        Mono<Order> orderMono = Mono.fromSupplier(()->{return orderRepository.save(order);});
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(orderMono,Order.class);
    }

    public Mono<ServerResponse> updateAddress(ServerRequest request){
        Integer orderId = Integer.parseInt(String.valueOf(request.attribute("name").orElse("")));
        String street = String.valueOf(request.attribute("street").orElse(""));
        String zip = String.valueOf(request.attribute("zip").orElse(""));
        Order order = orderRepository.findById(orderId).orElse(new Order());
        Address address = order.getM_Address();
        if(Objects.isNull(address)){
            address = createAddress(street, zip);
        }else {
            address.setZip(zip);
            address.setStreet(street);
        }
        order.setM_Address(address);
        cacheManager.refresh(orderId);
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.fromSupplier(()->{return orderRepository.save(order);}),Order.class);
    }

    public Mono<ServerResponse> payment(ServerRequest request){
        Integer orderId = Integer.parseInt(String.valueOf(request.attribute("name").orElse("")));
        Order orderSaved = orderRepository.findById(orderId).orElse(new Order());
        if (orderSaved.setM_OrderStatus(new Payment())) {
            orderRepository.save(orderSaved);

        }
        cacheManager.refresh(orderId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(orderSaved),Order.class);
    }

    public Mono<ServerResponse> delivery(ServerRequest request){
        Integer orderId = Integer.parseInt(String.valueOf(request.attribute("name").orElse("")));
        Order orderSaved = orderRepository.findById(orderId).orElse(new Order());
        if (orderSaved.setM_OrderStatus(new Delivery())) {
            orderRepository.save(orderSaved);
        }
        cacheManager.refresh(orderId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(orderSaved),Order.class);
    }


    public Mono<ServerResponse> findAllOrders(ServerRequest request){
        Flux<Order> orderList = Flux.fromStream(orderRepository.findAll().stream());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(orderList,Order.class);

    }

    public Mono<ServerResponse> findByOrderId(ServerRequest request){
        Integer id = Integer.parseInt(request.pathVariable("orderId"));
        Order order = cacheManager.getOrder(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(order),Order.class);
    }

    private Address createAddress(String street, String zip){

        Address address = new Address();
        address.setStreet(street);
        address.setZip(zip);
        return address;


    }
}
