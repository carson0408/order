package com.carson.order.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * ClassName OrderItem
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Entity
@Table(name = "order_item")
@Proxy(lazy = false)
public class OrderItem {

    @Column(name = "product_id")
    private String productId;
    private int qty;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_table_key")
    private int order_table_key;



    //JoinColumn中name是表中的外键名，referencedColumnName代表关联表的字段
    @ManyToOne
    @JoinColumn(name = "order_table",referencedColumnName = "id")
    @JsonBackReference
    private Order order;






    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getOrder_table_key() {
        return order_table_key;
    }

    public void setOrder_table_key(int order_table_key) {
        this.order_table_key = order_table_key;
    }



    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


}
