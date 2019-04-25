package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.User;
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
    public HandlerResponse getOrderList(@RequestParam(value="paid") boolean paid, Principal principal) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        System.out.println(paid);
        return new HandlerResponse(orderDao.findOrderByUserId(user.getUid(), paid), 0);
    }

    @RequestMapping(value = "/orders", method = POST)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public HandlerResponse createOrder(@RequestParam(value="paid") boolean paid, Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, paid);
        orderDao.createOrder(orderList);
        return new HandlerResponse(null, 0);
    }

    @RequestMapping(value = "/orders", method = PUT)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public HandlerResponse updateOrder(@RequestParam(value="paid") boolean paid, Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, paid);
        orderDao.updateOrder(orderList);
        return new HandlerResponse(null, 0);
    }

    @RequestMapping(value = "/orders", method = DELETE)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public HandlerResponse deleteOrder(Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, false);
        orderDao.deleteOrder(orderList);
        return new HandlerResponse(null, 0);
    }
}
