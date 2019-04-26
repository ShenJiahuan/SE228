package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.exception.IncorrectParameterException;
import com.shenjiahuan.eBook.response.HandlerResponse;
import com.shenjiahuan.eBook.util.CreateOrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @RequestMapping(value = "/orders", method = GET)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    public List<Object> getOrderList(@RequestParam(value="paid") boolean paid, Principal principal) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        System.out.println(paid);
        return orderDao.findOrderByUserId(user.getUid(), paid);
    }

    @RequestMapping(value = "/orders", method = POST)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void createOrder(@RequestParam(value="paid") boolean paid, Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        try {
            List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, paid);
            if (!orderDao.createOrder(orderList)) {
                throw new IncorrectParameterException("books not enough");
            }
        } catch (NullPointerException ex) {
            throw new IncorrectParameterException("order parameters incorrect");
        }
    }

    @RequestMapping(value = "/orders", method = PUT)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void updateOrder(@RequestParam(value="paid") boolean paid, Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        try {
            List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, paid);
            if (!orderDao.updateOrder(orderList)) {
                throw new IncorrectParameterException("books not enough");
            }
        } catch (NullPointerException ex) {
            throw new IncorrectParameterException("order parameters incorrect");
        }
    }

    @RequestMapping(value = "/orders", method = DELETE)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void deleteOrder(Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        try {
            List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, false);
            if (!orderDao.deleteOrder(orderList)) {
                throw new IncorrectParameterException("Unknown exception");
            }
        } catch (NullPointerException ex) {
            throw new IncorrectParameterException("order parameters incorrect");
        }
    }
}
