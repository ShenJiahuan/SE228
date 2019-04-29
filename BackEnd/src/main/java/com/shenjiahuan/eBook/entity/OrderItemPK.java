package com.shenjiahuan.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemPK implements Serializable {
    private int uid;
    private int bookId;
    private BigDecimal addTime;

    @Column(name = "uid")
    @Id
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Column(name = "book_id")
    @Id
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Column(name = "add_time")
    @Id
    public BigDecimal getAddTime() {
        return addTime;
    }

    public void setAddTime(BigDecimal addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemPK orderItemPK = (OrderItemPK) o;

        if (uid != orderItemPK.uid) return false;
        if (bookId != orderItemPK.bookId) return false;
        if (addTime != null ? !addTime.equals(orderItemPK.addTime) : orderItemPK.addTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + bookId;
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        return result;
    }
}
