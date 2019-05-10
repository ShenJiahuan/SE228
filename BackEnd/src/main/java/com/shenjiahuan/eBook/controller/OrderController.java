package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.dao.UserDao;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.exception.IncorrectParameterException;
import com.shenjiahuan.eBook.service.OrderService;
import com.shenjiahuan.eBook.service.UserService;
import com.shenjiahuan.eBook.util.CreateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class OrderController {
    @Autowired
    @Lazy
    private OrderService orderService;

    @Autowired
    @Lazy
    private UserService userService;

    @RequestMapping(value = "/orders", method = GET)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    public List<Object> getOrderList(@RequestParam(value="paid") boolean paid, Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        System.out.println(paid);
        List<Object> orders = orderService.findOrderByUserId(user.getUid());
        return orders;
    }

    @RequestMapping(value = "/orders", method = POST)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void createOrder(@RequestParam(value="paid") boolean paid, Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        try {
            Order order = CreateOrder.fromJsonStr(body, uid);
            if (!orderService.createOrder(order)) {
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
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        try {
            Order order = CreateOrder.fromJsonStr(body, uid);
            if (!orderService.createOrder(order)) {
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
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        try {
            /*
            List<OrderItem> orderItemList = CreateOrder.fromJsonStr(body, uid, false);
            if (!orderDao.deleteOrder(orderItemList)) {
                throw new IncorrectParameterException("Unknown exception");
            }
             */
        } catch (NullPointerException ex) {
            throw new IncorrectParameterException("order parameters incorrect");
        }
    }
}
