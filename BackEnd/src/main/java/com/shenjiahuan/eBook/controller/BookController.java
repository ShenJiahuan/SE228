package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.dao.BookDaoImp;
import com.shenjiahuan.eBook.exception.IncorrectParameterException;
import com.shenjiahuan.eBook.response.HandlerResponse;
import com.shenjiahuan.eBook.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class BookController {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private FileStorageService fileStorageService;

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
        if (limit <= 0) {
            throw new IncorrectParameterException("limit must be positive");
        }
        return bookDao.findTopBookList("hot", limit);
    }

    @RequestMapping(value = "/books/recommend", method = GET)
    public List<Book> getRecommendBookList(@RequestParam(value="limit") int limit) {
        if (limit <= 0) {
            throw new IncorrectParameterException("limit must be positive");
        }
        return bookDao.findTopBookList("score", limit);
    }

    @RequestMapping(value = "/upload/image", method = POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HandlerResponse uploadImage(@RequestParam("file") MultipartFile file, Principal principal) {
        String fileName = fileStorageService.storeFile(file);
        return new HandlerResponse(fileName, true);
    }

    @RequestMapping(value = "/books", method = POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HandlerResponse uploadBook(@RequestBody Book book) {
        bookDao.createBook(book);
        return new HandlerResponse(null, true);
    }
}
