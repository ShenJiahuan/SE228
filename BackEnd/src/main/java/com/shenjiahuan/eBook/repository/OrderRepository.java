package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findOrdersByUidOrderByPayTimeDesc(int userId);
}
