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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDaoImp implements OrderDao {

    @SuppressWarnings("unchecked")
    public List<Object> findOrderByUserId(int userId, boolean paid) {
        List<Object> orders = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
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

            orders = query.list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return orders;
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
    public boolean createOrder(List<Order> orders) {
        boolean success = true;
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
                } else {
                    int remain = (int) session
                            .createQuery("select remain from Book where bookId = :bookId")
                            .setParameter("bookId", order.getBookId())
                            .list().get(0);
                    if (remain < order.getCount()) {
                        throw new Exception("No enough books");
                    }
                    session
                            .createQuery("update Book set remain = remain - :order where bookId = :bookId")
                            .setParameter("order", order.getCount())
                            .setParameter("bookId", order.getBookId())
                            .executeUpdate();
                }
                session.save(order);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
            success = false;
        }
        return success;
    }

    public boolean updateOrder(List<Order> orders) {
        boolean success = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (Order order : orders) {
                if (order.getPurchased() == 0) {
                    String hql = "Delete from Order where bookId = :bookId and uid = :uid and purchased = 0";
                    Query query = session.createQuery(hql);
                    query.setParameter("bookId", order.getBookId());
                    query.setParameter("uid", order.getUid());
                    query.executeUpdate();
                    session.save(order);
                } else {
                    int remain = (int) session
                            .createQuery("select remain from Book where bookId = :bookId")
                            .setParameter("bookId", order.getBookId())
                            .list().get(0);
                    if (remain < order.getCount()) {
                        throw new Exception("No enough books");
                    }
                    session
                            .createQuery("update Book set remain = remain - :order where bookId = :bookId")
                            .setParameter("order", order.getCount())
                            .setParameter("bookId", order.getBookId())
                            .executeUpdate();

                    session
                            .createQuery("update Order set purchased = 1, purchaseTime = :purchaseTime" +
                                    " where uid = :uid and bookId = :bookId and purchased = 0")
                            .setParameter("purchaseTime", order.getPurchaseTime())
                            .setParameter("uid", order.getUid())
                            .setParameter("bookId", order.getBookId())
                            .executeUpdate();
                }
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
            success = false;
        }
        return success;
    }

    public boolean deleteOrder(List<Order> orders) {
        boolean success = true;
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
            success = false;
        }
        return success;
    }
}
