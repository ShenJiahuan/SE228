package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.OrderItem;

import java.util.List;

public interface OrderDao {
    List<Object> findOrderByUserId(int userId, boolean paid);

    boolean createOrder(List<OrderItem> orderItems);

    boolean updateOrder(List<OrderItem> orderItems);

    boolean deleteOrder(List<OrderItem> orderItems);
}
