package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Item;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDaoImp implements OrderDao {

    @Autowired
    SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Object> findOrderByUserId(int userId) {
        List<Object> orders = null;
        Session session = sessionFactory.getCurrentSession();
        orders = session.createQuery("from Order o where uid = :uid order by payTime desc")
                    .setParameter("uid", userId)
                    .list();
        return orders;
    }

    public boolean createOrder(Order order) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        session.save(order);
        return success;
    }
}
