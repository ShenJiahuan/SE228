package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.entity.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> findOrderByUserId(int userId);

    void createOrder(Order order) throws IOException;

    void cartToOrder(Order order) throws IOException;

    List<Object> getStatus(BigDecimal from, BigDecimal to);

    List<Object> getUserPurchase(BigDecimal from, BigDecimal to);

    List<Order> findAllOrders();
}
