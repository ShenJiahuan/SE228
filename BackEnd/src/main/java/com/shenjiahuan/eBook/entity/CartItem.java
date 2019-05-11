package com.shenjiahuan.eBook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@PrimaryKeyJoinColumn(name = "item_id")
public class CartItem extends Item {

    private int uid;

    private BigDecimal addTime;

    public CartItem() {}

    public CartItem(int bookId, int count, int uid, BigDecimal addTime) {
        super(bookId, count);
        this.uid = uid;
        this.addTime = addTime;
    }

    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Column(name = "add_time")
    public BigDecimal getAddTime() {
        return addTime;
    }

    public void setAddTime(BigDecimal addTime) {
        this.addTime = addTime;
    }

}
