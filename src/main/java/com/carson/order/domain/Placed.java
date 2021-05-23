package com.carson.order.domain;

/**
 * ClassName Placed
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
public class Placed extends OrderStatus {
    public Placed() {
        super(0);
    }

    @Override
    public OrderStatus next() {
        return new Payment();
    }
}
