package com.shenjiahuan.eBook.service.impl;

import com.shenjiahuan.eBook.dao.CartDao;
import com.shenjiahuan.eBook.entity.CartItem;
import com.shenjiahuan.eBook.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cartDao;

    @Override
    public List<CartItem> findCartItemByUserId(int userId) {
        return cartDao.findCartItemByUserId(userId);
    }

    @Override
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

    @Override
    public void updateCart(CartItem item) {
        CartItem previousCartItem = cartDao.findCartItemByUserIdAndBookId(item.getUid(), item.getBookId());
        cartDao.deleteCart(previousCartItem);
        cartDao.addCart(item);
    }

    @Override
    public void deleteCart(CartItem item) {
        cartDao.deleteCart(item);
    }
}
