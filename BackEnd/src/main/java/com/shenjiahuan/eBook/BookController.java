package com.shenjiahuan.eBook;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.manage.ManageBook;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class BookController {
    private static ManageBook manageBook = new ManageBook();
    @RequestMapping(value = "/books/{id}", method = GET)
    public Book getBookInfo(@PathVariable("id") int id) {
        return manageBook.getBook(id);
    }

    @RequestMapping(value = "/books", method = GET)
    public List<Book> getBookList(@RequestParam(value="keyword") String keyword) {
        return manageBook.getRelatedBookList(keyword);
    }

    @RequestMapping(value = "/books/hot", method = GET)
    public List<Book> getHotBookList(@RequestParam(value="limit") int limit) {
        return manageBook.getTopBookList("hot", limit);
    }

    @RequestMapping(value = "/books/recommend", method = GET)
    public List<Book> getRecommendBookList(@RequestParam(value="limit") int limit) {
        return manageBook.getTopBookList("score", limit);
    }
}
