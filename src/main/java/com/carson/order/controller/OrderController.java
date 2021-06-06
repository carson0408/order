package com.carson.order.controller;


import com.carson.order.annotation.Limit;
import com.carson.order.domain.*;
import com.carson.order.repository.OrderRepository;
import com.carson.order.response.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * ClassName OrderController
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class OrderController {


    @Resource
    private OrderRepository orderRepo;


    @PostMapping("/orders")
    @Limit(key = "orders", period = 60, count = 5, name = "ordersLimit", prefix = "limit")
    public Result placeOrder(@RequestBody OrderItem orderItem,
                             @RequestParam String name,
                             @RequestParam String street,
                             @RequestParam String zip) {
        try {
            Collection<OrderItem> orderItems = new ArrayList<>();
            ((ArrayList<OrderItem>) orderItems).add(orderItem);
            Order order = Order.builder().withName(name).withAddress(createAddress(street, zip)).withItems(orderItems).build();
            System.out.println("order:" + order);
            return Result.ok(orderRepo.save(order));
        }catch (Exception e){
            return Result.error(500,e.getMessage());
        }
    }


    @PutMapping("/orders/updateAddress")
    @Limit(key = "updateAddress", period = 60, count = 5, name = "updateAddressLimit", prefix = "limit")
    public Result updateAddress(@RequestParam String street,@RequestParam Integer orderId,@RequestParam String zip){
        Order order = orderRepo.findById(orderId).orElse(new Order());
        Address address = order.getM_Address();
        if(Objects.isNull(address)){
            address = createAddress(street, zip);
        }else {
            address.setZip(zip);
            address.setStreet(street);
        }
        order.setM_Address(address);
        return Result.ok(orderRepo.save(order));
    }


    @PostMapping("/orders/payment")
    @Limit(key = "payment", period = 60, count = 5, name = "paymentLimit", prefix = "limit")
    public Result payment(@RequestParam Integer orderId) {
        Order orderSaved2 = orderRepo.findById(orderId).orElse(new Order());
        if (orderSaved2.setM_OrderStatus(new Payment()))
            orderRepo.save(orderSaved2);
        return Result.ok(orderSaved2);
    }

    @PostMapping("/orders/delivery")
    @Limit(key = "delivery", period = 60, count = 5, name = "deliveryLimit", prefix = "limit")
    public Result delivery(@RequestParam Integer orderId) {
        Order orderSaved2 = orderRepo.findById(orderId).orElse(new Order());
        if (orderSaved2.setM_OrderStatus(new Delivery()))
            orderRepo.save(orderSaved2);
        return Result.ok(orderSaved2);
    }




    private Address createAddress(String street,String zip){

        Address address = new Address();
        address.setStreet(street);
        address.setZip(zip);
        return address;


    }

}
