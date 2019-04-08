package com.shenjiahuan.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

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

        if (uid != cartPK.uid) return false;
        if (bookId != cartPK.bookId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + bookId;
        return result;
    }
}
