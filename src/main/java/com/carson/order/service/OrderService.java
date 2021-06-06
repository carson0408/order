package com.carson.order.service;

import com.carson.order.domain.*;
import com.carson.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * ClassName OrderService
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Service
public class OrderService {

    private OrderRepository orderRepository;

    public Order orders(OrderItem orderItem,String name,String street,String zip){
        Collection<OrderItem> orderItems = new ArrayList<>();
        ((ArrayList<OrderItem>) orderItems).add(orderItem);
        Order order = Order.builder().withName(name).withAddress(createAddress(street, zip)).withItems(orderItems).build();
        return  orderRepository.save(order);
    }

    public Order updateAddress(String street,Integer orderId,String zip){
        Order order = orderRepository.findById(orderId).orElse(new Order());
        Address address = order.getM_Address();
        if(Objects.isNull(address)){
            address = createAddress(street, zip);
        }else {
            address.setZip(zip);
            address.setStreet(street);
        }
        order.setM_Address(address);
        return orderRepository.save(order);
    }

    public Order payment(Integer orderId){
        Order orderSaved = orderRepository.findById(orderId).orElse(new Order());
        if (orderSaved.setM_OrderStatus(new Payment())) {
            orderRepository.save(orderSaved);
        }
        return orderSaved;
    }

    public Order delivery(Integer orderId){
        Order orderSaved = orderRepository.findById(orderId).orElse(new Order());
        if (orderSaved.setM_OrderStatus(new Delivery())) {
            orderRepository.save(orderSaved);
        }
        return orderSaved;
    }

    private Address createAddress(String street, String zip){

        Address address = new Address();
        address.setStreet(street);
        address.setZip(zip);
        return address;


    }
}
