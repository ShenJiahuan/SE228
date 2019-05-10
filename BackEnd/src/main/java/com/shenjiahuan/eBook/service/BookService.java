package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    BookDao bookDao;

    public Book findBookById(int bookId) {
        return bookDao.findBookById(bookId);
    }

    public List<Book> findTopBookList(String type, int limit) {
        return bookDao.findTopBookList(type, limit);
    }

    public List<Book> findRelatedBookList(String keyword) {
        return findRelatedBookList(keyword);
    }

    public boolean createBook(Book book) {
        return createBook(book);
    }
}
