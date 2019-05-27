package com.shenjiahuan.eBook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="\"order\"")
public class Order {

    private int uid;
    private int orderId;
    private BigDecimal payTime;

    public Order() {}

    public Order(int uid, BigDecimal payTime) {
        this.uid = uid;
        this.payTime = payTime;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    @JsonIgnore
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    @Column(name = "pay_time")
    public BigDecimal getPayTime() {
        return payTime;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPayTime(BigDecimal payTime) {
        this.payTime = payTime;
    }

    private List<OrderItem> items;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "order")
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
