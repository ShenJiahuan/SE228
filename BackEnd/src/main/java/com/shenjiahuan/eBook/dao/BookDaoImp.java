package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class BookDaoImp implements BookDao {
    private static Logger logger = Logger.getLogger(BookDaoImp.class);

    public Book findBookById(int bookId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "from Book where bookId = :bookId";
        Query query = session.createQuery(hql);
        query.setParameter("bookId", bookId);
        @SuppressWarnings("unchecked")
        List<Book> books = query.list();
        session.getTransaction().commit();
        return books.size() != 0 ? books.get(0) : null;
    }

    public List<Book> findTopBookList(String type, int limit) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql;
        if (type.equals("hot")) {
            hql = "from Book order by hot desc";
        } else {
            hql = "from Book order by score desc";
        }
        Query query = session.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<Book> books = query.setMaxResults(limit).list();
        session.getTransaction().commit();
        return books.size() != 0 ? books : null;
    }


    public List<Book> findRelatedBookList(String keyword) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "from Book where title like :keyword";
        Query query = session.createQuery(hql);
        query.setParameter("keyword", "%" + keyword + "%");
        @SuppressWarnings("unchecked")
        List<Book> books = query.list();
        session.getTransaction().commit();
        return books.size() != 0 ? books : null;
    }

    public void createBook(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            int bookId = (int) session.createQuery("select max(bookId) from Book").list().get(0) + 1;
            book.setBookId(bookId);
            session.save(book);
            String srcdir = System.getProperty("java.io.tmpdir");
            String destdir = getClass().getClassLoader().getResource(".").getFile() + "/static/images/";
            System.out.println(getClass().getClassLoader().getResource(".").getFile());
            Files.move(Paths.get(srcdir + book.getImg()), Paths.get(destdir + book.getImg()));
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

}
