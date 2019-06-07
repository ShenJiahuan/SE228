package com.shenjiahuan.eBook.dao.impl;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.repository.BookRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    private static Logger logger = Logger.getLogger(BookDaoImpl.class);

    @Autowired
    BookRepository bookRepository;

    @Value("${image-dir}")
    String imageDir;

    public Book findBookById(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findTopBookList(String type, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        if (type.equals("hot")) {
            return bookRepository.findAllByOrderBySnapshot_HotDesc(pageable);
        } else if (type.equals("recommend")) {
            return bookRepository.findAllByOrderBySnapshot_ScoreDesc(pageable);
        } else {
            return null;
        }
    }

    public List<Book> findRelatedBookList(String keyword) {
        return bookRepository.findBooksBySnapshot_TitleContaining(keyword);
    }

    public void createOrUpdateBook(Book book) throws IOException {
//        if (book.getSnapshot() != null) {
//            String srcDir = System.getProperty("java.io.tmpdir");
//            if (!new File(imageDir, book.getSnapshot().getImg()).exists()) {
//                Files.move(Paths.get(srcDir + "/" + book.getSnapshot().getImg()), Paths.get(imageDir + book.getSnapshot().getImg()));
//            }
//            System.out.println(imageDir);
//        }
        bookRepository.saveAndFlush(book);
    }

    public void deleteBookById(int bookId) {
        bookRepository.deleteById(bookId);
    }
}
