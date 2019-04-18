package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImp implements OrderDao {

    public List<Object> findOrderByUserId(int userId, boolean paid) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql;
        if (paid) {
            hql =
                    "From Order A, Book B " +
                    "where A.bookId = B.bookId and A.uid = :uid and A.purchased = :paid " +
                    "order by A.purchaseTime desc";
        } else {
            hql =
                    "From Order A, Book B " +
                    "where A.bookId = B.bookId and A.uid = :uid and A.purchased = :paid " +
                    "order by A.addTime desc";
        }
        Query query = session.createQuery(hql);
        query.setParameter("uid", userId);
        query.setParameter("paid", paid ? (byte) 1 : (byte) 0);
        @SuppressWarnings("unchecked")
        List<Object> orders = query.list();
        session.getTransaction().commit();
        return orders.size() != 0 ? orders : null;
    }

    public void createOrder(List<Order> orders) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (Order order : orders) {
                session.save(order);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }

    }

    public void updateOrder(List<Order> orders) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (Order order : orders) {
                session.update(order);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
