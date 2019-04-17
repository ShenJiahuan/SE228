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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
        Byte purchased = paid ? (byte) 1 : (byte) 0;
        int addTime = (int)(new Date().getTime() / 1000);
        System.out.println(paid);
        JsonParser parser = new JsonParser();
        JsonArray orders = parser.parse(body).getAsJsonObject().getAsJsonArray("orders");
        List<Order> orderList = new ArrayList<>();
        for (JsonElement order : orders) {
            JsonObject orderJsonObject = order.getAsJsonObject();
            int bookId = orderJsonObject.get("id").getAsInt();
            int count = orderJsonObject.get("count").getAsInt();
            if (paid) {
                orderList.add(new Order(uid, bookId, count, addTime, addTime, purchased));
            } else {
                orderList.add(new Order(uid, bookId, count, addTime, null, purchased));
            }

        }
        orderDao.createOrder(orderList);
        return new HandlerResponse(null, true);
    }
}
