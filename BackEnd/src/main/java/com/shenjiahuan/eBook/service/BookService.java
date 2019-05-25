package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.entity.BookSnapshot;

import java.io.IOException;
import java.util.List;

public interface BookService {
    Book findBookById(int bookId);

    List<Book> findAll();

    List<Book> findTopBookList(String type, int limit);

    List<Book> findRelatedBookList(String keyword);

    void createBook(BookSnapshot bookSnapshot) throws IOException;

    void updateBook(BookSnapshot bookSnapshot) throws IOException;

    void deleteBookById(int bookId);
}
