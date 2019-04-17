package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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

    public void createOrder(List<Order> orders) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (Order order : orders) {
                session.save(order);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        session.close();
    }
}
