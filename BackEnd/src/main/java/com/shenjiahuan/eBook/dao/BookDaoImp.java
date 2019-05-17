package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.repository.BookRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

@Repository
public class BookDaoImp implements BookDao {
    private static Logger logger = Logger.getLogger(BookDaoImp.class);

    @Autowired
    BookRepository bookRepository;

    @Value("${image-dir}")
    String imageDir;

    public Book findBookById(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> findTopBookList(String type, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        if (type.equals("hot")) {
            return bookRepository.findAllByOrderByHotDesc(pageable);
        } else if (type.equals("score")) {
            return bookRepository.findAllByOrderByScoreDesc(pageable);
        } else {
            return null;
        }
    }

    public List<Book> findRelatedBookList(String keyword) {
        logger.debug(keyword);
        logger.debug(bookRepository.findAll().size());
        return bookRepository.findBooksByTitleContaining(keyword);
    }

    public void createOrUpdateBook(Book book) throws IOException {
        String srcDir = System.getProperty("java.io.tmpdir");
        if (!new File(imageDir, book.getImg()).exists()) {
            Files.move(Paths.get(srcDir + "/" + book.getImg()), Paths.get(imageDir + book.getImg()));
        }
        bookRepository.save(book);
        System.out.println(imageDir);
    }
}
