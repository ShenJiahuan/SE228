package com.shenjiahuan.eBook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "book_snapshot")
@IdClass(BookSnapshotPK.class)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class BookSnapshot {

    private int bookId;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String author;
    private String publisher;
    private String publishDate;
    private Integer pages;
    @NotNull(message = "Price is mandatory")
    private Double price;
    private String decoration;
    @NotBlank(message = "ISBN is mandatory")
    private String isbn;
    private Double hot;
    private Double score;
    private String bookDesc;
    private String originalName;
    private int remain;
    private BigDecimal snapTime;

    private String imgFileName;

    @Id
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "publisher")
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "publish_date")
    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Basic
    @Column(name = "pages")
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "decoration")
    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    @Basic
    @Column(name = "ISBN")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "hot")
    public Double getHot() {
        return hot;
    }

    public void setHot(Double hot) {
        this.hot = hot;
    }

    @Basic
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Basic
    @Column(name = "book_desc")
    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    @Basic
    @Column(name = "original_name")
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Basic
    @Column(name = "remain")
    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    @Id
    public BigDecimal getSnapTime() {
        return snapTime;
    }

    public void setSnapTime(BigDecimal snapTime) {
        this.snapTime = snapTime;
    }

    @Transient
    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookSnapshot bookSnapshot = (BookSnapshot) o;
        return bookId == bookSnapshot.bookId && snapTime.equals(bookSnapshot.snapTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, snapTime);
    }
}
