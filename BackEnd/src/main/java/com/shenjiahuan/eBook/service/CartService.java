package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.entity.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> findCartItemByUserId(int userId);

    void addCart(CartItem item);

    void updateCart(CartItem item);

    void deleteCart(CartItem item);
}
