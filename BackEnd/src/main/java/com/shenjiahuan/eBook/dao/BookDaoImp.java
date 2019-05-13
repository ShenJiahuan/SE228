package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
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
    SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public Book findBookById(int bookId) {
        List<Book> books = null;
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Book where bookId = :bookId";
        Query query = session.createQuery(hql);
        query.setParameter("bookId", bookId);
        books = query.list();
        return (books != null && books.size() != 0) ? books.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public List<Book> findTopBookList(String type, int limit) {
        List<Book> books = null;
        Session session = sessionFactory.getCurrentSession();
        String hql;
        if (type.equals("hot")) {
            hql = "from Book order by hot desc";
        } else {
            hql = "from Book order by score desc";
        }
        Query query = session.createQuery(hql);
        books = query.setMaxResults(limit).list();
        return (books != null && books.size() != 0) ? books : null;
    }

    @SuppressWarnings("unchecked")
    public List<Book> findRelatedBookList(String keyword) {
        List<Book> books = null;
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Book where title like :keyword";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", "%" + keyword + "%");

        books = query.list();
        return (books != null && books.size() != 0) ? books : null;
    }

    public boolean createBook(Book book) throws IOException {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
        String srcdir = System.getProperty("java.io.tmpdir");
        System.out.println(srcdir);
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String destdir = jarFile.getParentFile().toString() + "/static/images/";
        Files.move(Paths.get(srcdir + "/" + book.getImg()), Paths.get(destdir + book.getImg()));
        return success;
    }

    public boolean updateBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.update(book);
        return true;
    }

}
