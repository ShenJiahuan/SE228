package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookDao {
    Book findBookById(int bookId);

    List<Book> findTopBookList(String type, int limit);

    List<Book> findRelatedBookList(String keyword);

    boolean createBook(Book book) throws IOException;

    boolean updateBook(Book book);
}
