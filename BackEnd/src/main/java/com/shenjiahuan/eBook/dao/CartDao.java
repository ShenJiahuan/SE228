package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.CartItem;

import java.util.List;

public interface CartDao {
    List<CartItem> findCartItemByUserId(int userId);

    void addCart(CartItem item);

    CartItem findCartItemByUserIdAndBookId(int userId, int bookId);

    void deleteCart(CartItem item);

    void deleteCartItemByBookId(int bookId);
}
