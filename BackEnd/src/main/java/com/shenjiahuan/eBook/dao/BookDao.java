package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;

import java.util.List;

public interface BookDao {
    Book findBookById(int bookId);

    List<Book> findTopBookList(String type, int limit);

    List<Book> findRelatedBookList(String keyword);
}
