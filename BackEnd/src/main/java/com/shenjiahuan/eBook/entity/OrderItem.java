package com.shenjiahuan.eBook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@DiscriminatorValue("order")
public class OrderItem extends Item {
    public OrderItem() {}

    public OrderItem(int bookId, int count) {
        super(bookId, count);
    }

    private Order order;

    private BigDecimal snapTime;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "snap_time")
    public BigDecimal getSnapTime() {
        return snapTime;
    }

    public void setSnapTime(BigDecimal snapTime) {
        this.snapTime = snapTime;
    }

    private BookSnapshot snapshot;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "book_id", insertable = false, updatable = false),
            @JoinColumn(name = "snap_time", insertable = false, updatable = false)
    })
    public BookSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(BookSnapshot bookSnapshot) {
        this.snapshot = bookSnapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
