package com.carson.order.controller;


import com.carson.order.annotation.Limit;
import com.carson.order.domain.*;
import com.carson.order.response.Result;
import com.carson.order.service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

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
    private OrderService orderService;


    @PostMapping("/orders")
    @Limit(key = "orders", period = 60, count = 5, name = "ordersLimit", prefix = "limit")
    public Result placeOrder(@RequestBody OrderItem orderItem,
                             @RequestParam String name,
                             @RequestParam String street,
                             @RequestParam String zip) {
        try {
            return Result.ok(orderService.orders(orderItem, name, street, zip));
        }catch (Exception e){
            return Result.error(500,e.getMessage());
        }
    }


    @PutMapping("/orders/updateAddress")
    @Limit(key = "updateAddress", period = 60, count = 5, name = "updateAddressLimit", prefix = "limit")
    public Result updateAddress(@RequestParam String street,@RequestParam Integer orderId,@RequestParam String zip){
        return Result.ok(orderService.updateAddress(street, orderId, zip));
    }


    @PostMapping("/orders/payment")
    @Limit(key = "payment", period = 60, count = 5, name = "paymentLimit", prefix = "limit")
    public Result payment(@RequestParam Integer orderId) {
        return Result.ok(orderService.payment(orderId));
    }

    @PostMapping("/orders/delivery")
    @Limit(key = "delivery", period = 60, count = 5, name = "deliveryLimit", prefix = "limit")
    public Result delivery(@RequestParam Integer orderId) {
        return Result.ok(orderService.delivery(orderId));
    }


}
