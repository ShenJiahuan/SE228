package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findOrdersByUidOrderByPayTimeDesc(int userId);

    @Query("select oi.snapshot, sum(oi.count) from Order o inner join OrderItem oi on o.orderId = oi.order.orderId where o.payTime > ?1 and o.payTime < ?2 group by oi.snapshot order by sum(oi.count) desc")
    List<Object> getStatus(BigDecimal from, BigDecimal to);
}
