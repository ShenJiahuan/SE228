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

import java.util.ArrayList;
import java.util.Arrays;
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

    private List<Integer> existOrder(List<Order> orders) {
        int size = orders.size();
        List<Integer> result = Arrays.asList(new Integer[size]);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (int i = 0; i < size; i++) {
                Order order = orders.get(i);
                String hql = "From Order where uid = :uid and bookId = :bookId and purchased = :purchased";
                Query query = session.createQuery(hql);
                query.setParameter("uid", order.getUid());
                query.setParameter("bookId", order.getBookId());
                query.setParameter("purchased", order.getPurchased());
                @SuppressWarnings("unchecked")
                List<Order> list = query.list();
                if (list.size() == 0) {
                    result.set(i, 0);
                } else {
                    result.set(i, list.get(0).getCount());
                }
                session.getTransaction().commit();
            }
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return result;
    }

    // FIXME: may cause books in the cart over the upper bound
    public void createOrUpdateOrder(List<Order> orders) {
        List<Integer> previousOrders = existOrder(orders);
        int size = orders.size();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (int i = 0; i < size; i++) {
                Order order = orders.get(i);
                if (order.getPurchased() == 0) {
                    order.setCount(order.getCount() + previousOrders.get(i));
                    String hql = "Delete from Order where bookId = :bookId and uid = :uid and purchased = 0";
                    Query query = session.createQuery(hql);
                    query.setParameter("bookId", order.getBookId());
                    query.setParameter("uid", order.getUid());
                    query.executeUpdate();
                }
                session.save(order);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    public void deleteOrder(List<Order> orders) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (Order order : orders) {
                String hql = "Delete from Order where bookId = :bookId and uid = :uid";
                Query query = session.createQuery(hql);
                query.setParameter("bookId", order.getBookId());
                query.setParameter("uid", order.getUid());
                query.executeUpdate();
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
