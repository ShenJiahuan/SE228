package com.shenjiahuan.eBook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "book")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Book {

    private Integer bookId;
    private BigDecimal snapTime;
    private BookSnapshot snapshot;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "book_id")
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Column(name = "snap_time")
    public BigDecimal getSnapTime() {
        return snapTime;
    }

    public void setSnapTime(BigDecimal snapTime) {
        this.snapTime = snapTime;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumns( {
            @JoinColumn(name = "book_id", referencedColumnName = "book_id", insertable = false, updatable = false),
            @JoinColumn(name = "snap_time", referencedColumnName = "snap_time", insertable = false, updatable = false)
    })
    public BookSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(BookSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public List<CartItem> cartItems;

    @JsonIgnore
    @OneToMany(mappedBy = "bookId")
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
