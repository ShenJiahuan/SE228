package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class BookDaoImp implements BookDao {
    private static Logger logger = Logger.getLogger(BookDaoImp.class);


    @SuppressWarnings("unchecked")
    public Book findBookById(int bookId) {
        List<Book> books = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            String hql = "from Book where bookId = :bookId";
            Query query = session.createQuery(hql);
            query.setParameter("bookId", bookId);
            books = query.list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return (books != null && books.size() != 0) ? books.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public List<Book> findTopBookList(String type, int limit) {
        List<Book> books = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            String hql;
            if (type.equals("hot")) {
                hql = "from Book order by hot desc";
            } else {
                hql = "from Book order by score desc";
            }
            Query query = session.createQuery(hql);
            books = query.setMaxResults(limit).list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return (books != null && books.size() != 0) ? books : null;
    }

    @SuppressWarnings("unchecked")
    public List<Book> findRelatedBookList(String keyword) {
        List<Book> books = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            String hql = "from Book where title like :keyword";
            Query query = session.createQuery(hql);
            query.setParameter("keyword", "%" + keyword + "%");

            books = query.list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return (books != null && books.size() != 0) ? books : null;
    }

    public boolean createBook(Book book) {
        boolean success = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            int bookId = (int) session.createQuery("select max(bookId) from Book").list().get(0) + 1;
            book.setBookId(bookId);
            session.save(book);
            String srcdir = System.getProperty("java.io.tmpdir");
            System.out.println(srcdir);
            String destdir = BookDaoImp.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/static/images/";
            Files.move(Paths.get(srcdir + "/" + book.getImg()), Paths.get(destdir + book.getImg()));
            session.getTransaction().commit();
        } catch (Exception ex) {
            success = false;
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return success;
    }

}
