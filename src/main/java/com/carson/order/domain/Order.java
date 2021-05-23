package com.carson.order.domain;

import org.hibernate.annotations.Proxy;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * ClassName Order
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Table(name = "order_table")
@Entity
@Proxy(lazy = false)
public class Order {



    @OneToOne(mappedBy = "order",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    //@JsonBackReference
    private Address m_Address;

    @OneToOne(mappedBy = "order",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    private OrderStatus m_OrderStatus;





    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@JsonBackReference
    private Collection<OrderItem> items;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "order_name")
    private String name;

    public Order() {
        items = new ArrayList<>();
        m_OrderStatus = new Placed();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public Collection<OrderItem> getItems() {
        return items;
    }

    public void setItems(Collection<OrderItem> items) {
        this.items = items;
    }

    public Address getM_Address() {
        return m_Address;
    }

    public void setM_Address(Address m_Address) {
        this.m_Address = m_Address;
    }

    public OrderStatus getM_OrderStatus() {
        return m_OrderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean setM_OrderStatus(OrderStatus m_OrderStatus) {
        if(m_OrderStatus==null){
            return false;
        }
        OrderStatus status = getM_OrderStatus();
        if(status==null){
            return false;
        }
        OrderStatus orderStatusN = status.next();
        if (orderStatusN.getState() == m_OrderStatus.getState()) {
            this.m_OrderStatus = orderStatusN;
            return true;
        } else
            return false;
    }


    private Order(Address address, Collection<OrderItem> items,String name) {
        this.m_Address = address;
        this.m_OrderStatus = new Placed();
        this.name = name;
        //需要与维持方建立关系，这里订单与地址一对一的关系，地址需要与订单建立关系
        m_Address.setOrder(this);
        this.items = items.stream().map(r->{
            r.setOrder(this);
            return r;}).collect(Collectors.toList());
        m_OrderStatus.setOrder(this);
    }

    public static OrderVOBuilder builder() {
        return new OrderVOBuilder();
    }

    public static class OrderVOBuilder{
        private Address address;
        private Collection<OrderItem> items;

        private String name;
        public OrderVOBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }
        public OrderVOBuilder withItems(Collection<OrderItem> items) {
            this.items = items;
            return this;
        }

        public OrderVOBuilder withName(String name){
            this.name = name;
            return this;
        }
        public Order build() {
            return new Order(address, items,name);
        }
    }
}
