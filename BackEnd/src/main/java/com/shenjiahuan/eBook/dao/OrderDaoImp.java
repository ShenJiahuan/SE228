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
    public List<Object> findPurchasedOrderByUserId(int userId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> order = criteriaQuery.from(Order.class);
        Predicate p1 = builder.and(builder.equal(order.get("uid"), userId));
        Predicate p2 = builder.and(builder.equal(order.get("purchased"), 1));
        criteriaQuery.where(p1, p2);
//        criteriaQuery.orderBy(builder.asc(order.get("purchase_time")));
        String hql = "From Order A, Book B where A.bookId = B.bookId and A.uid = :uid and A.purchased = 1";
        Query query = session.createQuery(hql);
        query.setParameter("uid", userId);
        List<Object> orders = query.getResultList();
        session.close();
        return orders.size() != 0 ? orders : null;
    }

    public List<Object> findUnpurchasedOrderByUserId(int userId) {
        return null;
    }
}
