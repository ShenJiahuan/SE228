package com.shenjiahuan.eBook.dao.impl;

import com.shenjiahuan.eBook.dao.CartDao;
import com.shenjiahuan.eBook.entity.CartItem;
import com.shenjiahuan.eBook.repository.CartRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    @Autowired
    CartRepository cartRepository;

    @SuppressWarnings("unchecked")
    public List<CartItem> findCartItemByUserId(int userId) {
        return cartRepository.findCartItemsByUidOrderByAddTimeDesc(userId);
    }

    public void addCart(CartItem item) {
        cartRepository.save(item);
    }

    public CartItem findCartItemByUserIdAndBookId(int userId, int bookId) {
        return cartRepository.findCartItemByUidAndBookId(userId, bookId);
    }

    public void deleteCart(CartItem item) {
        cartRepository.deleteCartItemByUidAndBookId(item.getUid(), item.getBookId());
    }

    public void deleteCartItemByBookId(int bookId) {
        cartRepository.deleteCartItemByBookId(bookId);
    }
}
