package com.shenjiahuan.eBook.dao.impl;

import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> findOrderByUserId(int userId) {
        return orderRepository.findOrdersByUidOrderByPayTimeDesc(userId);
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Object> getStatus(BigDecimal from, BigDecimal to) {
        return orderRepository.getStatus(from, to);
    }

    public List<Object> getUserPurchase(BigDecimal from, BigDecimal to) {
        return orderRepository.getUserPurchase(from, to);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAllByOrderByPayTimeDesc();
    }
}
