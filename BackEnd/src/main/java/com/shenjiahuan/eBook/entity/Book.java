package com.shenjiahuan.eBook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Book {
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
    private String img;
    private Double hot;
    private Double score;
    private String bookDesc;
    private String originalName;
    private int remain;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "book_id")
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
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId &&
                Double.compare(book.price, price) == 0 &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(publishDate, book.publishDate) &&
                Objects.equals(pages, book.pages) &&
                Objects.equals(decoration, book.decoration) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(img, book.img) &&
                Objects.equals(hot, book.hot) &&
                Objects.equals(score, book.score) &&
                Objects.equals(bookDesc, book.bookDesc) &&
                Objects.equals(originalName, book.originalName) &&
                Objects.equals(remain, book.remain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, publisher, publishDate, pages, price, decoration, isbn, img, hot, score, bookDesc, originalName, remain);
    }
}
