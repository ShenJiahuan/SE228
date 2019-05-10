package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public List<Object> findOrderByUserId(int userId) {
        return orderDao.findOrderByUserId(userId);
    }

    public boolean createOrder(Order order) {
        return orderDao.createOrder(order);
    }
}
