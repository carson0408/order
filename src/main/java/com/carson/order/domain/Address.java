package com.carson.order.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * ClassName Address
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Table(name = "address")
@Entity
@Proxy(lazy = false)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "street")
    private String street;
    @Column(name = "zip")
    private String zip;

    @OneToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_table",referencedColumnName = "id")
    @JsonBackReference
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public Address() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}