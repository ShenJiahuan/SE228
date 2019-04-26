package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Object> findOrderByUserId(int userId, boolean paid);

    boolean createOrder(List<Order> orders);

    boolean updateOrder(List<Order> orders);

    boolean deleteOrder(List<Order> orders);
}
