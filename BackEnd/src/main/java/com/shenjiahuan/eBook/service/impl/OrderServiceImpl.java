package com.shenjiahuan.eBook.service.impl;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.dao.CartDao;
import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.entity.CartItem;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;
import com.shenjiahuan.eBook.exception.IncorrectParameterException;
import com.shenjiahuan.eBook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    CartDao cartDao;

    @Autowired
    BookDao bookDao;

    @Override
    public List<Order> findOrderByUserId(int userId) {
        return orderDao.findOrderByUserId(userId);
    }

    @Override
    public void createOrder(Order order) throws IOException {
        for (OrderItem item : order.getItems()) {
            Book book = bookDao.findBookById(item.getBookId());
            if (book.getSnapshot().getRemain() - item.getCount() < 0) {
                throw new IncorrectParameterException("books not enough");
            }
            book.getSnapshot().setRemain(book.getSnapshot().getRemain() - item.getCount());
            bookDao.createOrUpdateBook(book);
            item.setSnapTime(book.getSnapTime());
        }
        orderDao.createOrder(order);
    }

    @Override
    public void cartToOrder(Order order) throws IOException {
        createOrder(order);
        for (OrderItem item : order.getItems()) {
            CartItem cartItem = cartDao.findCartItemByUserIdAndBookId(order.getUid(), item.getBookId());
            cartDao.deleteCart(cartItem);
        }
    }

    @Override
    public List<Object> getStatus(BigDecimal from, BigDecimal to) {
        return orderDao.getStatus(from, to);
    }

    @Override
    public List<Object> getUserPurchase(BigDecimal from, BigDecimal to) {
        return orderDao.getUserPurchase(from, to);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }
}
