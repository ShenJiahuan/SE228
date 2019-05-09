package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;

import java.util.List;

public interface OrderDao {
    List<Order> findOrderByUserId(int userId);

    boolean createOrder(Order order);
}
