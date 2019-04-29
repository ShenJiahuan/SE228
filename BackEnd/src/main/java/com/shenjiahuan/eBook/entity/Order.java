package com.shenjiahuan.eBook.entity;

import javax.persistence.*;

@Entity
@Table(name="\"Order\"")
public class Order {
    private int orderId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return orderId;
    }
}
