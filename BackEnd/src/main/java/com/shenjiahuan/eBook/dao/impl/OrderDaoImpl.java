package com.shenjiahuan.eBook.dao.impl;

import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.repository.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
