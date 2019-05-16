package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.repository.BookRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class BookDaoImp implements BookDao {
    private static Logger logger = Logger.getLogger(BookDaoImp.class);

    @Autowired
    BookRepository bookRepository;

    public Book findBookById(int bookId) {
        return bookRepository.getOne(bookId);
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
        return bookRepository.findBooksByTitleContaining(keyword);
    }

    public void createOrUpdateBook(Book book) throws IOException {
        String srcDir = System.getProperty("java.io.tmpdir");
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String destDir = jarFile.getParentFile().toString() + "/static/images/";
        if (!new File(destDir, book.getImg()).exists()) {
            Files.move(Paths.get(srcDir + "/" + book.getImg()), Paths.get(destDir + book.getImg()));
        }
        bookRepository.save(book);
        System.out.println(destDir);
    }
}
