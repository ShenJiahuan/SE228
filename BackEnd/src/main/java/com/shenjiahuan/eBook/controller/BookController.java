package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.dao.BookDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class BookController {

    @Autowired
    private BookDao bookDao;

    @RequestMapping(value = "/books/{id}", method = GET)
    public Book getBookInfo(@PathVariable("id") int id) {
        return bookDao.findBookById(id);
    }

    @RequestMapping(value = "/books", method = GET)
    public List<Book> getBookList(@RequestParam(value="keyword") String keyword) {
        return bookDao.findRelatedBookList(keyword);
    }

    @RequestMapping(value = "/books/hot", method = GET)
    public List<Book> getHotBookList(@RequestParam(value="limit") int limit) {
        return bookDao.findTopBookList("hot", limit);
    }

    @RequestMapping(value = "/books/recommend", method = GET)
    public List<Book> getRecommendBookList(@RequestParam(value="limit") int limit) {
        return bookDao.findTopBookList("score", limit);
    }
}
