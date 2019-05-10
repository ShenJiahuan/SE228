package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.exception.FileStorageException;
import com.shenjiahuan.eBook.exception.IncorrectParameterException;
import com.shenjiahuan.eBook.exception.NotFoundException;
import com.shenjiahuan.eBook.response.HandlerResponse;
import com.shenjiahuan.eBook.service.BookService;
import com.shenjiahuan.eBook.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private BookService bookService;

    @Autowired
    @Lazy
    private FileStorageService fileStorageService;

    @RequestMapping(value = "/books/{id}", method = GET)
    public Book getBookInfo(@PathVariable("id") int id) {
        Book book = bookService.findBookById(id);
        if (book == null) {
            throw new NotFoundException("book not found");
        }
        return book;
    }

    @RequestMapping(value = "/books", method = GET)
    public List<Book> getBookList(@RequestParam(value="keyword") String keyword) {
        List<Book> books = bookService.findRelatedBookList(keyword);
        if (books == null) {
            throw new NotFoundException("book not found");
        }
        return books;
    }

    @RequestMapping(value = "/books/hot", method = GET)
    public List<Book> getHotBookList(@RequestParam(value="limit") int limit) {
        if (limit <= 0) {
            throw new IncorrectParameterException("limit must be positive");
        }
        List<Book> books = bookService.findTopBookList("hot", limit);
        if (books == null) {
            throw new NotFoundException("book not found");
        } else if (books.size() < limit) {
            throw new IncorrectParameterException("limit too large");
        }
        return books;
    }

    @RequestMapping(value = "/books/recommend", method = GET)
    public List<Book> getRecommendBookList(@RequestParam(value="limit") int limit) {
        if (limit <= 0) {
            throw new IncorrectParameterException("limit must be positive");
        }
        List<Book> books = bookService.findTopBookList("score", limit);
        if (books == null) {
            throw new NotFoundException("book not found");
        } else if (books.size() < limit) {
            throw new IncorrectParameterException("limit too large");
        }
        return books;
    }

    @RequestMapping(value = "/upload/image", method = POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String uploadImage(@RequestParam("file") MultipartFile file, Principal principal) {
        String filename = null;
        try {
            filename = fileStorageService.storeFile(file);
        } catch (FileStorageException ex) {
            throw new IncorrectParameterException("cannot upload file");
        }
        return filename;
    }

    @RequestMapping(value = "/books", method = POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadBook(@RequestBody Book book) {
        if (!bookService.createBook(book)) {
            throw new IncorrectParameterException("error creating book");
        }
    }
}
