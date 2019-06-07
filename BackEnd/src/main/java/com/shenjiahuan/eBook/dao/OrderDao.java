package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findOrderByUserId(int userId);

    void createOrder(Order order);
}
