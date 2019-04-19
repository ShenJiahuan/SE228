package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Object> findOrderByUserId(int userId, boolean paid);

    void createOrUpdateOrder(List<Order> orders);

    void deleteOrder(List<Order> orders);
}
