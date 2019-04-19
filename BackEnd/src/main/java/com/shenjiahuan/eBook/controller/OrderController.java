package com.shenjiahuan.eBook.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.response.HandlerResponse;
import com.shenjiahuan.eBook.util.CreateOrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @RequestMapping(value = "/orders", method = GET)
    public HandlerResponse getOrderList(@RequestParam(value="paid") boolean paid, Principal principal) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        System.out.println(paid);
        return new HandlerResponse(orderDao.findOrderByUserId(user.getUid(), paid), true);
    }

    @RequestMapping(value = "/orders", method = POST)
    @ResponseBody
    public HandlerResponse createOrder(@RequestParam(value="paid") boolean paid, Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, paid);
        orderDao.createOrUpdateOrder(orderList);
        return new HandlerResponse(null, true);
    }

    @RequestMapping(value = "/orders", method = PUT)
    @ResponseBody
    public HandlerResponse updateOrder(Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, false);
        orderDao.createOrUpdateOrder(orderList);
        return new HandlerResponse(null, true);
    }

    @RequestMapping(value = "/orders", method = DELETE)
    @ResponseBody
    public HandlerResponse deleteOrder(Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        int uid = user.getUid();
        List<Order> orderList = CreateOrderList.fromJsonStr(body, uid, false);
        orderDao.deleteOrder(orderList);
        return new HandlerResponse(null, true);
    }
}
