package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImp implements CartDao {

    @Autowired
    SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<CartItem> findCartItemByUserId(int userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from CartItem where uid = :uid order by addTime desc")
                .setParameter("uid", userId)
                .list();
    }

    public void addCart(CartItem item) {
        Session session = sessionFactory.getCurrentSession();
        session.save(item);
    }

    @SuppressWarnings("unchecked")
    public CartItem findCartItemByUserIdAndBookId(int userId, int bookId) {
        List<CartItem> cartItems;
        Session session = sessionFactory.getCurrentSession();
        cartItems =  session.createQuery("from CartItem where uid = :uid and bookId = :bookId order by addTime desc")
                .setParameter("uid", userId)
                .setParameter("bookId", bookId)
                .list();

        return cartItems.size() != 0 ? cartItems.get(0) : null;
    }

    public void deleteCart(CartItem item) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from CartItem where uid = :uid and bookId = :bookId")
                .setParameter("uid", item.getUid())
                .setParameter("bookId", item.getBookId())
                .executeUpdate();
    }
}
