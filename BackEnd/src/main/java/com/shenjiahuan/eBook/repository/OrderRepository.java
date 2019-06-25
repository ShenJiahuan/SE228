package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findOrdersByUidOrderByPayTimeDesc(int userId);

    @Query("select bs, sum(oi.count) " +
           "from Order o inner join OrderItem oi on o.orderId = oi.order.orderId inner join BookSnapshot bs on oi.snapshot.bookId = bs.bookId " +
           "where o.payTime > ?1 and o.payTime < ?2 and bs.snapTime >= all(select snapTime from BookSnapshot bs1 where bs.bookId = bs1.bookId)" +
           "group by bs " +
           "order by sum(oi.count) desc")
    List<Object> getStatus(BigDecimal from, BigDecimal to);

    @Query("select u.username, u.email, sum(oi.count * bs.price) " +
           "from Order o inner join OrderItem oi on o.orderId = oi.order.orderId inner join BookSnapshot bs on oi.bookId = bs.bookId and oi.snapTime = bs.snapTime inner join User u on o.uid = u.uid " +
           "where o.payTime > ?1 and o.payTime < ?2 " +
           "group by u.uid")
    List<Object> getUserPurchase(BigDecimal from, BigDecimal to);
}
