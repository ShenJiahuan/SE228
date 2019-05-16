package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.dao.BookDao;
import com.shenjiahuan.eBook.dao.CartDao;
import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.entity.CartItem;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.OrderItem;
import com.shenjiahuan.eBook.exception.IncorrectParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    CartDao cartDao;

    @Autowired
    BookDao bookDao;

    public List<Order> findOrderByUserId(int userId) {
        return orderDao.findOrderByUserId(userId);
    }

    public void createOrder(Order order) throws IOException {
        orderDao.createOrder(order);
        for (OrderItem item : order.getItems()) {
            Book book = bookDao.findBookById(item.getBookId());
            if (book.getRemain() - item.getCount() < 0) {
                throw new IncorrectParameterException("books not enough");
            }
            book.setRemain(book.getRemain() - item.getCount());
            bookDao.createOrUpdateBook(book);
        }
    }

    public void cartToOrder(Order order) throws IOException {
        createOrder(order);
        for (OrderItem item : order.getItems()) {
            CartItem cartItem = cartDao.findCartItemByUserIdAndBookId(order.getUid(), item.getBookId());
            cartDao.deleteCart(cartItem);
        }
    }
}
