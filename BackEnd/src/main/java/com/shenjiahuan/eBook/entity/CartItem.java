package com.shenjiahuan.eBook.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@DiscriminatorValue("cart")
public class CartItem extends Item {

    private Integer uid;

    private BigDecimal addTime;

    public CartItem() {}

    public CartItem(int bookId, int count, Integer uid, BigDecimal addTime) {
        super(bookId, count);
        this.uid = uid;
        this.addTime = addTime;
    }

    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Column(name = "add_time")
    public BigDecimal getAddTime() {
        return addTime;
    }

    public void setAddTime(BigDecimal addTime) {
        this.addTime = addTime;
    }

    private Book book;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
