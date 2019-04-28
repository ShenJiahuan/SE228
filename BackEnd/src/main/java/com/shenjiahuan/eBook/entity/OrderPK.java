package com.shenjiahuan.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class OrderPK implements Serializable {
    private int uid;
    private int bookId;
    private double addTime;

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
    public double getAddTime() {
        return addTime;
    }

    public void setAddTime(double addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPK orderPK = (OrderPK) o;
        return uid == orderPK.uid &&
                bookId == orderPK.bookId &&
                addTime == orderPK.addTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, bookId, addTime);
    }
}
