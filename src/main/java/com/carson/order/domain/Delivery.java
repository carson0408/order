package com.carson.order.domain;

/**
 * ClassName Delivery
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
public class Delivery extends OrderStatus {

    public Delivery() {
        super(2);//已发货

    }

    @Override
    public OrderStatus next() {
        return null;
    }
}
