package com.shenjiahuan.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CartPK implements Serializable {
    private int uid;
    private int bookId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartPK cartPK = (CartPK) o;
        return uid == cartPK.uid &&
                bookId == cartPK.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, bookId);
    }
}
