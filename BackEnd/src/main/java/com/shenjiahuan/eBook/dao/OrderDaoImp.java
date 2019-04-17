package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderDaoImp implements OrderDao {

    private static SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object> findOrderByUserId(int userId, boolean paid) {
        Session session = sessionFactory.openSession();
//        criteriaQuery.orderBy(builder.asc(order.get("purchase_time")));
        String hql = "From Order A, Book B where A.bookId = B.bookId and A.uid = :uid and A.purchased = :paid";
        Query query = session.createQuery(hql);
        query.setParameter("uid", userId);
        query.setParameter("paid", paid ? (byte) 1 : (byte) 0);
        List<Object> orders = query.getResultList();
        session.close();
        return orders.size() != 0 ? orders : null;
    }
}
