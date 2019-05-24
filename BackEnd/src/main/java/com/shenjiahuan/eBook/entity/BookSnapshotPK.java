package com.shenjiahuan.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

public class BookSnapshotPK implements Serializable {

    private int bookId;
    private BigDecimal snapTime;

    @Column(name = "book_id")
    public int getBookId() {
        return bookId;
    }

    @Column(name = "snap_time")
    public BigDecimal getSnapTime() {
        return snapTime;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setSnapTime(BigDecimal snapTime) {
        this.snapTime = snapTime;
    }
}
