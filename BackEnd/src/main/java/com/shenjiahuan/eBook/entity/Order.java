package com.shenjiahuan.eBook.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="\"Order\"")
@IdClass(OrderPK.class)
public class Order {
    private int uid;
    private int bookId;
    private Integer count;
    private BigDecimal addTime;
    private BigDecimal purchaseTime;
    private Byte purchased;

    public Order() {}

    public Order(int uid, int bookId, Integer count, BigDecimal addTime, BigDecimal purchaseTime, Byte purchased) {
        this.uid = uid;
        this.bookId = bookId;
        this.count = count;
        this.addTime = addTime;
        this.purchaseTime = purchaseTime;
        this.purchased = purchased;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (uid != order.uid) return false;
        if (bookId != order.bookId) return false;
        if (count != null ? !count.equals(order.count) : order.count != null) return false;
        if (addTime != null ? !addTime.equals(order.addTime) : order.addTime != null) return false;
        if (purchaseTime != null ? !purchaseTime.equals(order.purchaseTime) : order.purchaseTime != null) return false;
        if (purchased != null ? !purchased.equals(order.purchased) : order.purchased != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + bookId;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (purchaseTime != null ? purchaseTime.hashCode() : 0);
        result = 31 * result + (purchased != null ? purchased.hashCode() : 0);
        return result;
    }
}
