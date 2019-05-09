package com.shenjiahuan.eBook.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@PrimaryKeyJoinColumn(name = "item_id")
public class OrderItem extends Item {
    public OrderItem() {}

    public OrderItem(int bookId, int count) {
        super(bookId, count);
    }

    private Order order;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id", nullable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
