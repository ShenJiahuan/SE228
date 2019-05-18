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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return book;
    }

    @RequestMapping(value = "/books", method = GET)
    public List<Book> getBookList(@RequestParam(value="keyword") String keyword) {
        List<Book> books = bookService.findRelatedBookList(keyword);
        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No related books found");
        }
        return books;
    }

    @RequestMapping(value = "/books/top/{option}", method = GET)
    public List<Book> getTopBookList(@PathVariable(value="option") String option, @RequestParam(value="limit") int limit) {
        if (limit <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limit must be positive");
        }
        if (!option.equals("hot") && !option.equals("recommend")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found");
        }
        List<Book> books = bookService.findTopBookList(option, limit);
        if (books == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        } else if (books.size() < limit) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limit too large");
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

    @RequestMapping(value = "/books", method = {POST, PUT})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadOrUpdateBook(@Valid @RequestBody Book book) throws IOException {
        bookService.createOrUpdateBook(book);
    }
}
