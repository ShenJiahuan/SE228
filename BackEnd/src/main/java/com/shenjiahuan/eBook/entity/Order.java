package com.shenjiahuan.eBook.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="\"Order\"")
@IdClass(OrderPK.class)
public class Order {
    private int uid;
    private int bookId;
    private Integer count;
    private Double addTime;
    private Double purchaseTime;
    private Byte purchased;

    public Order() {}

    public Order(int uid, int bookId, Integer count, Double addTime, Double purchaseTime, Byte purchased) {
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
    public Double getAddTime() {
        return addTime;
    }

    public void setAddTime(Double addTime) {
        this.addTime = addTime;
    }

    @Basic
    @Column(name = "purchase_time")
    public Double getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Double purchaseTime) {
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
        return uid == order.uid &&
                bookId == order.bookId &&
                Objects.equals(addTime, order.addTime) &&
                Objects.equals(count, order.count) &&
                Objects.equals(purchaseTime, order.purchaseTime) &&
                Objects.equals(purchased, order.purchased);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, bookId, count, addTime, purchaseTime, purchased);
    }
}
