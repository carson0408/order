package com.carson.order.controller;

import com.carson.order.annotation.Limit;
import com.carson.order.domain.Order;
import com.carson.order.domain.OrderItem;
import com.carson.order.response.Result;
import com.carson.order.service.OrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * ClassName WebFluxOrderController
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@RestController
@RequestMapping("/flux")
public class WebFluxOrderController {

    @Resource
    private OrderService orderService;


    @PostMapping("/orders")
    @Limit(key = "orders", period = 60, count = 5, name = "ordersLimit", prefix = "limit")
    public Mono<Order> placeOrder(@RequestBody OrderItem orderItem,
                                  @RequestParam String name,
                                  @RequestParam String street,
                                  @RequestParam String zip) {
        try {
            return Mono.fromSupplier(()->{return orderService.orders(orderItem, name, street, zip);});
        }catch (Exception e){
            return Mono.error(e);
        }
    }


    @PutMapping("/orders/updateAddress")
    @Limit(key = "updateAddress", period = 60, count = 5, name = "updateAddressLimit", prefix = "limit")
    public Mono<Order> updateAddress(@RequestParam String street,@RequestParam Integer orderId,@RequestParam String zip){
        return Mono.fromSupplier(()->{return orderService.updateAddress(street, orderId, zip);});
    }


    @PostMapping("/orders/payment")
    @Limit(key = "payment", period = 60, count = 5, name = "paymentLimit", prefix = "limit")
    public Mono<Order> payment(@RequestParam Integer orderId) {
        return Mono.fromSupplier(()->{ return orderService.payment(orderId);});
    }

    @PostMapping("/orders/delivery")
    @Limit(key = "delivery", period = 60, count = 5, name = "deliveryLimit", prefix = "limit")
    public Mono<Order> delivery(@RequestParam Integer orderId) {
        return Mono.fromSupplier(()->{return orderService.delivery(orderId);});
    }


    @GetMapping("/orders/getAll")
    public Flux<Order> getAllOrders(){
        return Flux.fromStream(orderService.findAllOrders().stream());
    }

    @GetMapping("orders/{orderId}")
    public Mono<Order> findById(@PathVariable Integer orderId){
        return Mono.fromSupplier(()->{
            return orderService.findByOrderId(orderId);
        });
    }
}
