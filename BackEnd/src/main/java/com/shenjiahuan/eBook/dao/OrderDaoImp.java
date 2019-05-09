package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Item;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDaoImp implements OrderDao {

    @SuppressWarnings("unchecked")
    public List<Order> findOrderByUserId(int userId) {
        List<Order> orders = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            orders = session.createQuery("from Order where uid = :uid")
                        .setParameter("uid", userId)
                        .list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return orders;
    }

    public boolean createOrder(Order order) {
        boolean success = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            session.save(order);
            session.getTransaction().commit();
        } catch (Exception ex) {
            success = false;
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return success;
    }
}
