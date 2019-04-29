package com.shenjiahuan.eBook.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="\"OrderItem\"")
@IdClass(OrderItemPK.class)
public class OrderItem {
    private int uid;
    private int bookId;
    private Integer count;
    private BigDecimal addTime;
    private BigDecimal purchaseTime;
    private Byte purchased;
    private Integer orderId;

    public OrderItem() {}

    public OrderItem(int uid, int bookId, Integer count, BigDecimal addTime, BigDecimal purchaseTime, Byte purchased, Integer orderId) {
        this.uid = uid;
        this.bookId = bookId;
        this.count = count;
        this.addTime = addTime;
        this.purchaseTime = purchaseTime;
        this.purchased = purchased;
        this.orderId = orderId;
    }

    @Id
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Id
    @Column(name = "book_id")
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Id
    @Column(name = "add_time")
    public BigDecimal getAddTime() {
        return addTime;
    }

    public void setAddTime(BigDecimal addTime) {
        this.addTime = addTime;
    }

    @Basic
    @Column(name = "purchase_time")
    public BigDecimal getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(BigDecimal purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Basic
    @Column(name = "purchased")
    public Byte getPurchased() {
        return purchased;
    }

    public void setPurchased(Byte purchased) {
        this.purchased = purchased;
    }

    @Id
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (uid != orderItem.uid) return false;
        if (bookId != orderItem.bookId) return false;
        if (count != null ? !count.equals(orderItem.count) : orderItem.count != null) return false;
        if (addTime != null ? !addTime.equals(orderItem.addTime) : orderItem.addTime != null) return false;
        if (purchaseTime != null ? !purchaseTime.equals(orderItem.purchaseTime) : orderItem.purchaseTime != null) return false;
        if (purchased != null ? !purchased.equals(orderItem.purchased) : orderItem.purchased != null) return false;
        if (orderId != null ? !orderId.equals(orderItem.orderId) : orderItem.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 15 * result + bookId;
        result = 15 * result + (count != null ? count.hashCode() : 0);
        result = 15 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 15 * result + (purchaseTime != null ? purchaseTime.hashCode() : 0);
        result = 15 * result + (purchased != null ? purchased.hashCode() : 0);
        result = 15 * result + (orderId != null ? orderId.hashCode() : 0);
        return result;
    }
}
