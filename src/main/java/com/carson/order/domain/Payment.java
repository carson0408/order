package com.carson.order.domain;

/**
 * ClassName Payment
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
public class Payment extends OrderStatus {

    public Payment() {
        super(1);//表示已支付
    }

    public OrderStatus next() {
        return new Delivery();
    }
}
