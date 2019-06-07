package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.entity.BookSnapshot;
import com.shenjiahuan.eBook.entity.Image;
import com.shenjiahuan.eBook.service.BookService;
import com.shenjiahuan.eBook.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private ImageService imageService;

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

    @RequestMapping(value = "/books/all", method = GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Book> getAllBooks() {
        return bookService.findAll();
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
        try {
            return imageService.createImage(file);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot upload file");
        }
    }

    @RequestMapping(value = "/books", method = POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadBook(@Valid @RequestBody BookSnapshot bookSnapshot) throws IOException {
        bookService.createBook(bookSnapshot);
        imageService.updateImage(bookSnapshot);
    }

    @RequestMapping(value = "/books", method = PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateBook(@Valid @RequestBody BookSnapshot bookSnapshot) throws IOException {
        bookService.updateBook(bookSnapshot);
        if (bookSnapshot.getImgFileName() != null) {
            imageService.updateImage(bookSnapshot);
        }
    }

    @RequestMapping(value = "/books/{id}", method = DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@PathVariable("id") int id) {
        try {
            bookService.deleteBookById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    @RequestMapping(value = "/books/{id}/image", method = GET)
    public String getImage(@PathVariable("id") int id) {
        try {
            Image image = imageService.findByBookId(id);
            return "data:image/jpeg;base64," + image.getImgBase64();
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }
    }
}
