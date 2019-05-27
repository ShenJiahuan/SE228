package com.shenjiahuan.eBook.service.impl;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.dao.BookSnapshotDao;
import com.shenjiahuan.eBook.dao.CartDao;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.entity.BookSnapshot;
import com.shenjiahuan.eBook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao bookDao;

    @Autowired
    CartDao cartDao;

    @Autowired
    BookSnapshotDao bookSnapshotDao;

    @Override
    public Book findBookById(int bookId) {
        return bookDao.findBookById(bookId);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findTopBookList(String type, int limit) {
        return bookDao.findTopBookList(type, limit);
    }

    @Override
    public List<Book> findRelatedBookList(String keyword) {
        return bookDao.findRelatedBookList(keyword);
    }

    @Override
    public void createBook(BookSnapshot bookSnapshot) throws IOException {
        BigDecimal snapTime = new BigDecimal(new Date().getTime() / 1000.0);
        Book book = new Book();
        bookDao.createOrUpdateBook(book);
        bookSnapshot.setBookId(book.getBookId());
        bookSnapshot.setSnapTime(snapTime);
        book.setSnapTime(snapTime);
        book.setSnapshot(bookSnapshot);
        bookDao.createOrUpdateBook(book);
    }

    @Override
    public void updateBook(BookSnapshot bookSnapshot) throws IOException {
        BigDecimal snapTime = new BigDecimal(new Date().getTime() / 1000.0);
        Book book = bookDao.findBookById(bookSnapshot.getBookId());
        bookSnapshot.setSnapTime(snapTime);
        book.setSnapTime(snapTime);
        book.setSnapshot(bookSnapshot);
        bookDao.createOrUpdateBook(book);
    }

    @Override
    public void deleteBookById(int bookId) {
        cartDao.deleteCartItemByBookId(bookId);
        bookDao.deleteBookById(bookId);
    }
}
