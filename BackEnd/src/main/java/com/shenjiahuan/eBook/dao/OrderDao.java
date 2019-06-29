package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao {
    List<Order> findOrderByUserId(int userId);

    void createOrder(Order order);

    List<Object> getStatus(BigDecimal from, BigDecimal to);

    List<Object> getUserPurchase(BigDecimal from, BigDecimal to);

    List<Order> findAllOrders();
}
