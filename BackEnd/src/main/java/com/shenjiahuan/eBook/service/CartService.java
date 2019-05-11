package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.dao.CartDao;
import com.shenjiahuan.eBook.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    CartDao cartDao;

    public List<CartItem> findCartItemByUserId(int userId) {
        return cartDao.findCartItemByUserId(userId);
    }

    public void addCart(CartItem item) {
        CartItem previousCartItem = cartDao.findCartItemByUserIdAndBookId(item.getUid(), item.getBookId());
        if (previousCartItem == null) {
            cartDao.addCart(item);
        } else {
            item.setCount(item.getCount() + previousCartItem.getCount());
            cartDao.deleteCart(previousCartItem);
            cartDao.addCart(item);
        }
    }

    public void updateCart(CartItem item) {
        CartItem previousCartItem = cartDao.findCartItemByUserIdAndBookId(item.getUid(), item.getBookId());
        cartDao.deleteCart(previousCartItem);
        cartDao.addCart(item);
    }

    public void deleteCart(CartItem item) {
        cartDao.deleteCart(item);
    }
}
