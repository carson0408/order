package com.carson.order.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * ClassName OrderStatus
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Table(name = "order_status")
@Entity
@Proxy(lazy = false)
@ToString
public class OrderStatus {

    protected int state = -1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_table",referencedColumnName = "id")
    @JsonBackReference
    private Order order;


    public OrderStatus(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    //切换的时候记得把其他的都要copy过来，仅有状态发生改变
    public OrderStatus next() {
        OrderStatus orderStatus = null;
        if (state == 0) {
            state = state+1;
        }
        else if (state == 1) {
            state = state+1;
        } else {
            state = -1;
        }
        return this;
    }
}
