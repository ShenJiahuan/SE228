package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

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
                        "From OrderItem A, Book B " +
                                "where A.bookId = B.bookId and A.uid = :uid and A.purchased = :paid " +
                                "order by A.purchaseTime desc";
            } else {
                hql =
                        "From OrderItem A, Book B " +
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

    private List<Integer> existOrder(List<OrderItem> orderItems) {
        int size = orderItems.size();
        List<Integer> result = Arrays.asList(new Integer[size]);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (int i = 0; i < size; i++) {
                OrderItem orderItem = orderItems.get(i);
                String hql = "From OrderItem where uid = :uid and bookId = :bookId and purchased = :purchased";
                Query query = session.createQuery(hql);
                query.setParameter("uid", orderItem.getUid());
                query.setParameter("bookId", orderItem.getBookId());
                query.setParameter("purchased", orderItem.getPurchased());
                @SuppressWarnings("unchecked")
                List<OrderItem> list = query.list();
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
    public boolean createOrder(List<OrderItem> orderItems) {
        boolean success = true;
        List<Integer> previousOrders = existOrder(orderItems);
        int size = orderItems.size();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            Order order = new Order();
            if (orderItems.get(0).getPurchased() == 1) {
                session.save(order);
            }
            for (int i = 0; i < size; i++) {
                OrderItem orderItem = orderItems.get(i);
                if (orderItem.getPurchased() == 0) {
                    orderItem.setCount(orderItem.getCount() + previousOrders.get(i));
                    String hql = "Delete from OrderItem where bookId = :bookId and uid = :uid and purchased = 0";
                    Query query = session.createQuery(hql);
                    query.setParameter("bookId", orderItem.getBookId());
                    query.setParameter("uid", orderItem.getUid());
                    query.executeUpdate();
                } else {
                    int remain = (int) session
                            .createQuery("select remain from Book where bookId = :bookId")
                            .setParameter("bookId", orderItem.getBookId())
                            .list().get(0);
                    if (remain < orderItem.getCount()) {
                        throw new Exception("No enough books");
                    }
                    session
                            .createQuery("update Book set remain = remain - :order where bookId = :bookId")
                            .setParameter("order", orderItem.getCount())
                            .setParameter("bookId", orderItem.getBookId())
                            .executeUpdate();
                    orderItem.setOrderId(order.getOrderId());
                }
                session.save(orderItem);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
            success = false;
        }
        return success;
    }

    public boolean updateOrder(List<OrderItem> orderItems) {
        boolean success = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            Order order = new Order();
            if (orderItems.get(0).getPurchased() == 1) {
                session.save(order);
            }
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getPurchased() == 0) {
                    String hql = "Delete from OrderItem where bookId = :bookId and uid = :uid and purchased = 0";
                    Query query = session.createQuery(hql);
                    query.setParameter("bookId", orderItem.getBookId());
                    query.setParameter("uid", orderItem.getUid());
                    query.executeUpdate();
                    session.save(orderItem);
                } else {
                    int remain = (int) session
                            .createQuery("select remain from Book where bookId = :bookId")
                            .setParameter("bookId", orderItem.getBookId())
                            .list().get(0);
                    if (remain < orderItem.getCount()) {
                        throw new Exception("No enough books");
                    }
                    session
                            .createQuery("update Book set remain = remain - :order where bookId = :bookId")
                            .setParameter("order", orderItem.getCount())
                            .setParameter("bookId", orderItem.getBookId())
                            .executeUpdate();

                    session
                            .createQuery("update OrderItem set purchased = 1, purchaseTime = :purchaseTime, orderId = :orderId" +
                                    " where uid = :uid and bookId = :bookId and purchased = 0")
                            .setParameter("purchaseTime", orderItem.getPurchaseTime())
                            .setParameter("uid", orderItem.getUid())
                            .setParameter("bookId", orderItem.getBookId())
                            .setParameter("orderId", order.getOrderId())
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

    public boolean deleteOrder(List<OrderItem> orderItems) {
        boolean success = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            for (OrderItem orderItem : orderItems) {
                String hql = "Delete from OrderItem where bookId = :bookId and uid = :uid";
                Query query = session.createQuery(hql);
                query.setParameter("bookId", orderItem.getBookId());
                query.setParameter("uid", orderItem.getUid());
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
