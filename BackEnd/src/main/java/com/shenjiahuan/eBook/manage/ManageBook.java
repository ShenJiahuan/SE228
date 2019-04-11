package com.shenjiahuan.eBook.manage;

import com.shenjiahuan.eBook.entity.Book;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ManageBook {
    private static SessionFactory factory;
    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    private static Logger logger = Logger.getLogger(ManageBook.class);
    public Book getBook(int bookId) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = builder.createQuery(Book.class);
        Root<Book> book = criteriaQuery.from(Book.class);
        Predicate p = builder.and(builder.equal(book.get("bookId"), bookId));
        criteriaQuery.where(p);
        List<Book> books = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return books.size() != 0 ? books.get(0) : null;
    }

    public List<Book> getTopBookList(String type, int limit) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = builder.createQuery(Book.class);
        Root<Book> book = criteriaQuery.from(Book.class);
        criteriaQuery.orderBy(builder.desc(book.get(type)));
        List<Book> books = session.createQuery(criteriaQuery).setMaxResults(limit).getResultList();
        session.close();
        return books.size() != 0 ? books : null;
    }

    public List<Book> getRelatedBookList(String keyword) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = builder.createQuery(Book.class);
        Root<Book> book = criteriaQuery.from(Book.class);
        Predicate p = builder.and(builder.like(book.get("title"), "%" + keyword + "%"));
        criteriaQuery.where(p);
        criteriaQuery.orderBy(builder.desc(book.get("bookId")));
        List<Book> books = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return books.size() != 0 ? books : null;
    }
}
